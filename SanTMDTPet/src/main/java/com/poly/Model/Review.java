package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Reviews") // Gắn với bảng 'Reviews' trong cơ sở dữ liệu
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id") // Tên cột trong cơ sở dữ liệu
    private int reviewId; // ID đánh giá

    @Column(name = "rating", nullable = false) // Xếp hạng (từ 1 đến 5)
    private int rating;

    @Column(name = "content") // Nội dung đánh giá
    private String comment; // Đổi tên thành 'content' để phù hợp với SQL

    @Column(name = "review_date", nullable = false) // Ngày đánh giá
    @Temporal(TemporalType.DATE)
    private Date reviewDate; // Ngày đánh giá, mặc định GETDATE() trong SQL

    @Column(name = "image") // Ảnh đính kèm
    private String image;

    @ManyToOne // Ánh xạ đến bảng InvoiceItems
    @JoinColumn(name = "invoice_item_id", nullable = false) // Tên cột trong bảng InvoiceItems
    private InvoiceItem invoiceItem; // Mã hóa đơn tương ứng

    @ManyToOne // Ánh xạ đến bảng Users
    @JoinColumn(name = "users_id", nullable = false) // Tên cột trong bảng Users
    private User user; // Người dùng viết đánh giá
}
