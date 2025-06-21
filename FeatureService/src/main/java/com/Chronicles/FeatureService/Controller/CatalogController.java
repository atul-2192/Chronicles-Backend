package com.Chronicles.FeatureService.Controller;


import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.Entity.CatalogFeature;
import com.Chronicles.FeatureService.Service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/features")
    public ResponseEntity<List<CatalogFeature>> getAllFeatures() {
        return ResponseEntity.ok(catalogService.getAllFeatures());
    }
    @PostMapping("/addFeature")
    public ResponseEntity<CatalogFeature> addFeature(@RequestBody CatalogFeature catalogFeature) {
        CatalogFeature newCatalogue=catalogService.addFeature(catalogFeature);
        return ResponseEntity.ok(newCatalogue);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        Path path = Paths.get("src/main/resources/static/pdfs/" + filename);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> handlePurchase(@RequestBody Cargo request) {
        catalogService.handlePurchase(request);
        return ResponseEntity.ok("Purchase processed successfully");
    }
}
