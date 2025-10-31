package com.AS.Student_Attendance.restcontroller;

import com.AS.Student_Attendance.entity.Attendance;
import com.AS.Student_Attendance.entity.Students;
import com.AS.Student_Attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportsRestController {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping("/defaulters")
    public List<Map<String, Object>> getDefaulterList(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("percentageThreshold") double percentageThreshold) {

        List<Attendance> records = attendanceRepository.findByAttendanceDateBetween(startDate, endDate);

        // Group attendance by student
        Map<Students, List<Attendance>> attendanceByStudent = records.stream()
                .filter(a -> a.getStudent() != null)
                .collect(Collectors.groupingBy(Attendance::getStudent));

        List<Map<String, Object>> defaulters = new ArrayList<>();
        for (Map.Entry<Students, List<Attendance>> entry : attendanceByStudent.entrySet()) {
            Students student = entry.getKey();
            List<Attendance> attList = entry.getValue();
            long presentCount = attList.stream().filter(a -> a.getStatus().name().equalsIgnoreCase("PRESENT")).count();
            double percentage = attList.size() > 0 ? (presentCount * 100.0) / attList.size() : 0.0;
            if (percentage < percentageThreshold) {
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("id", student.getId());
                studentInfo.put("firstName", student.getFirstName());
                studentInfo.put("lastName", student.getLastName());
                studentInfo.put("rollNumber", student.getRollNumber());
                studentInfo.put("email", student.getEmail());
                studentInfo.put("branch", student.getBranch() != null ? student.getBranch().toString() : null);
                studentInfo.put("attendancePercentage", percentage);
                defaulters.add(studentInfo);
            }
        }
        return defaulters;
    }

    private List<Map<String, Object>> getDefaulters(LocalDate startDate, LocalDate endDate, double percentageThreshold) {
        List<Attendance> records = attendanceRepository.findByAttendanceDateBetween(startDate, endDate);
        Map<Students, List<Attendance>> attendanceByStudent = records.stream()
                .filter(a -> a.getStudent() != null)
                .collect(Collectors.groupingBy(Attendance::getStudent));
        List<Map<String, Object>> defaulters = new ArrayList<>();
        for (Map.Entry<Students, List<Attendance>> entry : attendanceByStudent.entrySet()) {
            Students student = entry.getKey();
            List<Attendance> attList = entry.getValue();
            long presentCount = attList.stream().filter(a -> a.getStatus().name().equalsIgnoreCase("PRESENT")).count();
            double percentage = attList.size() > 0 ? (presentCount * 100.0) / attList.size() : 0.0;
            if (percentage < percentageThreshold) {
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("id", student.getId());
                studentInfo.put("firstName", student.getFirstName());
                studentInfo.put("lastName", student.getLastName());
                studentInfo.put("rollNumber", student.getRollNumber());
                studentInfo.put("email", student.getEmail());
                studentInfo.put("branch", student.getBranch() != null ? student.getBranch().toString() : null);
                studentInfo.put("attendancePercentage", percentage);
                defaulters.add(studentInfo);
            }
        }
        return defaulters;
    }

    @GetMapping("/defaulters/export/pdf")
    public ResponseEntity<ByteArrayResource> exportDefaultersPdf(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("percentageThreshold") double percentageThreshold) {
        List<Map<String, Object>> defaulters = getDefaulters(startDate, endDate, percentageThreshold);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Defaulter List"));
            document.add(new Paragraph("Date Range: " + startDate + " to " + endDate));
            document.add(new Paragraph("Threshold: " + percentageThreshold + "%"));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(5);
            table.addCell("Roll Number");
            table.addCell("Name");
            table.addCell("Email");
            table.addCell("Branch");
            table.addCell("Attendance (%)");
            for (Map<String, Object> d : defaulters) {
                table.addCell(String.valueOf(d.get("rollNumber")));
                table.addCell(d.get("firstName") + " " + d.get("lastName"));
                table.addCell(String.valueOf(d.get("email")));
                table.addCell(String.valueOf(d.get("branch")));
                table.addCell(String.format("%.2f", (Double)d.get("attendancePercentage")));
            }
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=defaulters.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/defaulters/export/excel")
    public ResponseEntity<ByteArrayResource> exportDefaultersExcel(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("percentageThreshold") double percentageThreshold) {
        List<Map<String, Object>> defaulters = getDefaulters(startDate, endDate, percentageThreshold);
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Defaulter List");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Roll Number");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Branch");
        header.createCell(4).setCellValue("Attendance (%)");
        int rowIdx = 1;
        for (Map<String, Object> d : defaulters) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(String.valueOf(d.get("rollNumber")));
            row.createCell(1).setCellValue(d.get("firstName") + " " + d.get("lastName"));
            row.createCell(2).setCellValue(String.valueOf(d.get("email")));
            row.createCell(3).setCellValue(String.valueOf(d.get("branch")));
            row.createCell(4).setCellValue((Double)d.get("attendancePercentage"));
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            // handle exception
        }
        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=defaulters.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    @GetMapping("/attendance-trend")
    public List<Map<String, Object>> getAttendanceTrend(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> records = attendanceRepository.findByAttendanceDateBetween(startDate, endDate);
        Map<LocalDate, List<Attendance>> byDate = records.stream()
                .collect(Collectors.groupingBy(Attendance::getAttendanceDate));
        List<Map<String, Object>> trend = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            List<Attendance> dayRecords = byDate.getOrDefault(date, Collections.emptyList());
            long present = dayRecords.stream().filter(a -> a.getStatus().name().equalsIgnoreCase("PRESENT")).count();
            long total = dayRecords.size();
            double percentage = total > 0 ? (present * 100.0) / total : 0.0;
            Map<String, Object> day = new HashMap<>();
            day.put("date", date.toString());
            day.put("attendancePercentage", percentage);
            trend.add(day);
        }
        return trend;
    }
}
