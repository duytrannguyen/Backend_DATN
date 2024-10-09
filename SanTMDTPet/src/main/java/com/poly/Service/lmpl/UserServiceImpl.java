package com.poly.Service.lmpl;

import static com.poly.constant.ApiConstant.API_COMMUNE;
import static com.poly.constant.ApiConstant.API_DISTRICT;
import static com.poly.constant.ApiConstant.API_PROVINCE;
import static com.poly.constant.ApiConstant.HTM;
import static com.poly.constant.GoogleConstant.GCLI;
import static com.poly.constant.GoogleConstant.GCLS;
import static com.poly.constant.GoogleConstant.GOOGLE_GRANT_TYPE;
import static com.poly.constant.GoogleConstant.GOOGLE_LINK_GET_TOKEN;
import static com.poly.constant.GoogleConstant.GOOGLE_LINK_GET_USER_INFO;
import static com.poly.constant.GoogleConstant.GOOGLE_REDIRECT_URI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.poly.Model.Address;
import com.poly.Model.User;
import com.poly.Reponsitory.RolesRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Service.EmailService;
import com.poly.Service.FileStorageService;
import com.poly.Service.SessionService;
import com.poly.Service.UserService;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.dto.request.UserRequest;
import com.poly.dto.response.ApiResponse;
import com.poly.dto.response.Commune;
import com.poly.dto.response.District;
import com.poly.dto.response.Province;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private org.modelmapper.ModelMapper ModelMapper;
    
    @Autowired
    private SessionService sessionService;

    @Override
    public boolean register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return false;
        }
        User user = ModelMapper.map(registerDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoleId(rolesRepository.findById(2).orElse(null));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(LoginDTO loginDTO) {
        if (!userRepository.existsByUsername(loginDTO.getUsername())) {
            return false;
        }
        User existingUser = userRepository.findByUsername(loginDTO.getUsername());
        if (!encoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
            return false;
        } else {
            sessionService.set("current_account", existingUser);
            sessionService.setTimeOut(1 * 24 * 60 * 60);
            return true;
        }
    }

    @Override
    public int countTotalCustomers() {
        return (int) userRepository.count(); // Assuming count() returns total user count
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByGender(Boolean gender) {
        return userRepository.findAllByGender(gender);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer username) {
        userRepository.deleteById(username);
    }

    @Override
    public int getTotalUsers() {
        return (int) userRepository.count(); // Assuming count() returns total user count
    }

    @Override
    public int getTotalProducts() {
        // Assuming there is a ProductRepository
        // return productRepository.countTotalProducts();
        return 0; // Placeholder, replace with actual implementation
    }

    private final com.poly.repo.RoleRepo roleRepo;

    private final com.poly.repo.UserRepo userRepo;

    private final com.poly.repo.ProvinceRepo provinceRepo;

    private final com.poly.repo.DistrictRepo districtRepo;

    private final com.poly.repo.CommuneRepo communeRepo;

    private final FileStorageService fileStorageService;

    private final com.poly.repo.AddressRepo addressRepo;

    private final EmailService emailService;

    private Integer id = 0;

    private final RestTemplate restTemplate = new RestTemplate();
    

    @Override
    public String getTokenGoogle(String code) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GOOGLE_LINK_GET_TOKEN))
                .POST(
                        HttpRequest.BodyPublishers.ofString("client_id=" + GCLI +
                                "&client_secret=" + GCLS +
                                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                                "&code=" + code +
                                "&grant_type=" + GOOGLE_GRANT_TYPE)
                )
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        String responseBody = "";
        String accessToken = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                responseBody = response.body();
            } else {
                System.out.println("Error: " + response.statusCode());
            }
            if (!(responseBody.isEmpty())) {
                JsonObject jobj = JsonParser.parseString(responseBody).getAsJsonObject();
                accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    @Override
    public User GoogleAccountGetUserInfo(String accessToken) {

        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .GET()
                .build();

        String responseBody = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                responseBody = response.body();
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject jobj = JsonParser.parseString(responseBody).getAsJsonObject();
        System.out.println(jobj);
        User user = new User();
        user.setUsername(jobj.get("given_name").toString().replaceAll("\"", ""));
        user.setFullName(jobj.get("name").toString().replaceAll("\"", ""));
        user.setEmail(jobj.get("email").toString().replaceAll("\"", ""));
        user.setProfileImage(jobj.get("picture").toString().replaceAll("\"", ""));
        user.setRoleId(roleRepo.findById(2).orElse(null));
        user.setPassword("null");
        user.setBirthDate(new Date());
        user.setGender(true);
        user.setPhone("null");

        if (userRepo.existsByEmail(user.getEmail())) {
            user = userRepo.findByEmail(user.getEmail());
        }
        return user;
    }

    @Override
    public User login(UserRequest userRequest) {
        User user = findByEmail(userRequest.getEmail());
        if (user != null && encoder.matches(user.getPassword(), userRequest.getPassword())) {
            id = user.getUsersId();
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User register(UserRequest userRequest, MultipartFile file) {

        User user = new User();
        Address address = new Address();

        if (!userRepo.existsByEmail(userRequest.getEmail())) {

            user.setFullName(userRequest.getFullName());
            user.setUsername(userRequest.getUserName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(encoder.encode(userRequest.getPassword()));
            user.setPhone(userRequest.getPhoneNumber());

            if (convertStringToDate(userRequest.getBirthDate()) != null) {

                user.setBirthDate(convertStringToDate(userRequest.getBirthDate()));
                user.setGender(userRequest.getGender());
                address.setStreet(userRequest.getStreetName());

                com.poly.Model.Province province = new com.poly.Model.Province(Integer.parseInt(userRequest.getIdProvince()), userRequest.getProvince());
                if (!provinceRepo.existsById(province.getProvinceId())) {

                    com.poly.Model.Province save = provinceRepo.save(province);
                    address.setProvince(save);
                } else {

                    Optional<com.poly.Model.Province> byId = provinceRepo.findById(province.getProvinceId());
                    address.setProvince(byId.orElse(null));
                }
                com.poly.Model.District district = new com.poly.Model.District(Integer.parseInt(userRequest.getIdDistrict()), userRequest.getDistrict(), province);
                if (!districtRepo.existsById(district.getDistrict_id())) {

                    com.poly.Model.District save = districtRepo.save(district);
                    address.setDistrict(save);
                } else {

                    Optional<com.poly.Model.District> byId = districtRepo.findById(district.getDistrict_id());
                    address.setDistrict(byId.orElse(null));
                }
                com.poly.Model.Commune commune = new com.poly.Model.Commune(Integer.parseInt(userRequest.getIdCommune()), userRequest.getCommune(), district);
                if (!communeRepo.existsById(commune.getCommune_id())) {

                    com.poly.Model.Commune save = communeRepo.save(commune);
                    address.setCommune(save);
                } else {

                    Optional<com.poly.Model.Commune> byId = communeRepo.findById(commune.getCommune_id());
                    address.setCommune(byId.orElse(null));
                }
                if (file != null) {
                    UUID uuid = UUID.randomUUID();
                    fileStorageService.save(file, uuid);// Save file to server
                    String fileUUID = uuid + (file.getOriginalFilename() != null ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')) : "");
                    user.setProfileImage(fileUUID);
                    user.setRoleId(roleRepo.findById(2).orElse(null));
                    String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", user.getPassword(), false);
                    emailService.send(user.getEmail(), buildEmail, "Register success");
                    User save = userRepo.save(user);
                    address.setUser(user);
                    addressRepo.save(address);
                }
            }
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<Province> fetchProvinces() {
        ApiResponse response = restTemplate.getForObject(API_PROVINCE, ApiResponse.class);
        if (response != null && response.getError() == 0) {
            return response.getData();
        }
        return null;
    }

    @Override
    public List<District> fetchDistricts(String provinceId) {
        ApiResponse response = restTemplate.getForObject(API_DISTRICT + provinceId + HTM, ApiResponse.class);
        if (response != null && response.getError() == 0) {
            List<Province> provinceResponse = response.getData();
            return provinceResponse
                    .stream()
                    .map(province -> new District(province.getId(), province.getName()))
                    .toList();
        }
        return null;
    }

    @Override
    public List<Commune> fetchCommunes(String districtId) {
        ApiResponse response = restTemplate.getForObject(API_COMMUNE + districtId + HTM, ApiResponse.class);
        if (response != null && response.getError() == 0) {
            List<Province> provinceResponse = response.getData();
            return provinceResponse
                    .stream()
                    .map(province -> new Commune(province.getId(), province.getName()))
                    .toList();
        }
        return null;
    }

    @Override
    public boolean forgotPassword(String email) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            UUID uuid = UUID.randomUUID();
            user.setPassword(uuid.toString());
            String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", uuid.toString(), true);
            if (emailService.send(user.getEmail(), buildEmail, "Forgot password")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User changePassword(Integer id, UserRequest userRequest) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            if (user.getPassword().equals(userRequest.getCurrentPassword())) {
                if (userRequest.getNewPassword().equals(userRequest.getConfirmNewPassword())) {
                    user.setPassword(userRequest.getConfirmNewPassword());
                    String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", "", true);
                    emailService.send(user.getEmail(), buildEmail, "You Just Change Password");
                    return userRepo.save(user);
                }
            }
        }
        return null;
    }

    @Override
    public User edit(Integer id, UserRequest userRequest, MultipartFile file) {

        User user = userRepo.findByUsersId(id);

        if (user != null) {
            user.setFullName(userRequest.getFullName());
            user.setUsername(userRequest.getUserName());
            user.setPhone(userRequest.getPhoneNumber());
            if (convertStringToDate(userRequest.getBirthDate()) != null) {
                user.setBirthDate(convertStringToDate(userRequest.getBirthDate()));
                user.setGender(userRequest.getGender());
                if (file != null) {
                    UUID uuid = UUID.randomUUID();
                    fileStorageService.save(file, uuid);// Save file to server
                    String fileUUID = uuid + (file.getOriginalFilename() != null ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')) : "");
                    user.setProfileImage(fileUUID);
                    User save = userRepo.save(user);
                    return save;
                } else {
                    User save = userRepo.save(user);
                    return save;
                }
            }
        }
        return null;
    }

    @Override
    public Address addAddress(Integer id, UserRequest userRequest) {
        Address address = new Address();
        User user = userRepo.findByUsersId(id);
        if (user != null) {

            address.setStreet(userRequest.getStreetName());

            com.poly.Model.Province province = new com.poly.Model.Province(Integer.parseInt(userRequest.getIdProvince()), userRequest.getProvince());
            if (!provinceRepo.existsById(province.getProvinceId())) {

                com.poly.Model.Province save = provinceRepo.save(province);
                address.setProvince(save);
            } else {

                Optional<com.poly.Model.Province> byId = provinceRepo.findById(province.getProvinceId());
                address.setProvince(byId.orElse(null));
            }
            com.poly.Model.District district = new com.poly.Model.District(Integer.parseInt(userRequest.getIdDistrict()), userRequest.getDistrict(), province);
            if (!districtRepo.existsById(district.getDistrict_id())) {

                com.poly.Model.District save = districtRepo.save(district);
                address.setDistrict(save);
            } else {

                Optional<com.poly.Model.District> byId = districtRepo.findById(district.getDistrict_id());
                address.setDistrict(byId.orElse(null));
            }
            com.poly.Model.Commune commune = new com.poly.Model.Commune(Integer.parseInt(userRequest.getIdCommune()), userRequest.getCommune(), district);
            if (!communeRepo.existsById(commune.getCommune_id())) {

                com.poly.Model.Commune save = communeRepo.save(commune);
                address.setCommune(save);
            } else {

                Optional<com.poly.Model.Commune> byId = communeRepo.findById(commune.getCommune_id());
                address.setCommune(byId.orElse(null));
            }
            address.setUser(user);
            System.out.println(address);
            return addressRepo.save(address);
        }
        return null;
    }

    @Override
    public List<Address> getAddress(Integer id) {
        System.out.println(addressRepo.findByUsers_id(id).get(0));
        return addressRepo.findByUsers_id(id);
    }

    @Override
    public Address deleteAddress(Integer id, Integer addressId) {
        Address address = addressRepo.findByUsers_idAndId(id, addressId);
        if (address != null) {
            address.setStatus(true);
            return addressRepo.save(address);
        }
        return null;
    }

    public Date convertStringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
