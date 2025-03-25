//package com.mycompany.grocerystorepos.model;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class InvoicePrinter {
//
//    public static void generateInvoicePDF(int invoiceId, String customerName, String email, String phone,
//                                          List<Product> cartItems, double totalAmount, double discountAmount, double finalAmount) {
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//
//        try {
//            // Lấy đường dẫn tới file font TrueType (có thể sử dụng bất kỳ font hỗ trợ ASCII)
//            File fontFile = new File("D:\\doan\\project_java_swing\\src\\main\\java\\font\\Arial Unicode Font\\Arial-Unicode-Bold-Italic.ttf");
//            PDTrueTypeFont font = PDTrueTypeFont.loadTTF(document, fontFile);  // Tải font TrueType
//
//            PDPageContentStream contentStream = new PDPageContentStream(document, page);
//            contentStream.setFont(font, 12);  // Sử dụng font TrueType cho văn bản
//
//            // Thêm tiêu đề
//            contentStream.beginText();
//            contentStream.newLineAtOffset(200, 750);  // Vị trí của tiêu đề
//            contentStream.showText("INVOICE");
//            contentStream.endText();
//
//            // Thêm thông tin khách hàng
//            contentStream.beginText();
//            contentStream.newLineAtOffset(50, 700);
//            contentStream.showText("Customer Name: " + customerName);
//            contentStream.newLineAtOffset(0, -20);
//            contentStream.showText("Email: " + email);
//            contentStream.newLineAtOffset(0, -20);
//            contentStream.showText("Phone: " + phone);
//            contentStream.endText();
//
//            // Thêm thông tin sản phẩm
//            contentStream.beginText();
//            contentStream.newLineAtOffset(50, 600);
//            contentStream.showText("Product Name       Quantity       Price        Total Price");
//            contentStream.newLineAtOffset(0, -20);
//
//            // Duyệt và thêm sản phẩm vào bảng
//            for (Product product : cartItems) {
//                contentStream.showText(String.format("%-20s %-10d %-10.2f %-10.2f",
//                        product.getProductName(),
//                        product.getQuantity(),
//                        product.getPrice(),
//                        product.getTotalPrice()));
//                contentStream.newLineAtOffset(0, -20);
//            }
//            contentStream.endText();
//
//            // Thêm thông tin thanh toán
//            contentStream.beginText();
//            contentStream.newLineAtOffset(50, 300);
//            contentStream.showText("Total Amount: " + totalAmount);
//            contentStream.newLineAtOffset(0, -20);
//            contentStream.showText("Discount: " + discountAmount);
//            contentStream.newLineAtOffset(0, -20);
//            contentStream.showText("Final Amount: " + finalAmount);
//            contentStream.endText();
//
//            contentStream.close();
//
//            // Lưu file PDF vào thư mục "bill"
//            String filePath = "D:\\doan\\project_java_swing\\bill\\invoice_" + invoiceId + ".pdf";
//            File file = new File(filePath);
//            document.save(file);  // Lưu tài liệu PDF vào file
//            document.close();
//
//            System.out.println("Invoice has been created and saved at: " + filePath);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
