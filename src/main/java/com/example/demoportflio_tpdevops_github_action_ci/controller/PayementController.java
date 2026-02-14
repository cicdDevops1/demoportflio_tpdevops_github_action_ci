package com.example.demoportflio_tpdevops_github_action_ci.controller;
/*import com.example.demoportflio.model.Apropos;
import com.example.demoportflio.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*package com.example.demoportflio.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
        String apiUrl = "https://api.test.bictorys.com/pay/v1/charges?payment_type=" + request.getPaymentType();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", "public-xxxxxxxxx"); // remplace avec ta clé

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount());
        body.put("currency", "XOF");
        body.put("country", "SN");
        body.put("successRedirectUrl", "https://example.com/success");
        body.put("ErrorRedirectUrl", "https://example.com/error");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
}
*//*@GetMapping("d/{id}")
public ResponseEntity<String> getPortfolioPage(@PathVariable Long id, @PathVariable String slug) {
    Apropos propos = aproposService.findBySectionUserId(id);

    if (propos == null) return ResponseEntity.notFound().build();

    User user = propos.getSection().getUser();

    String photoUrl = propos.getPhoto() != null && !propos.getPhoto().isEmpty()
            ? propos.getPhoto()
            : "https://tse2.mm.bing.net/th/id/OIP.WHkyw61qv4qCHQ_FbpgJ-gHaHa?r=0&rs=1&pid=ImgDetMain&o=7&rm=3";

    String gitUrl = (propos.getGit() != null && !propos.getGit().isEmpty()) ? propos.getGit() : "#";
    String linkedinUrl = (propos.getLinkedin() != null && !propos.getLinkedin().isEmpty()) ? propos.getLinkedin() : "#";

    String html = "<!DOCTYPE html>\n" +
            "<html lang=\"fr\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Portfolio - " + user.getName() + "</title>\n" +
            "    <!-- Open Graph -->\n" +
            "    <meta property=\"og:type\" content=\"website\" />\n" +
            "    <meta property=\"og:title\" content=\"" + propos.getTitre() + "\" />\n" +
            "    <meta property=\"og:description\" content=\"" + propos.getProfile() + "\" />\n" +
            "    <meta property=\"og:image\" content=\"" + photoUrl + "\" />\n" +
            "    <meta property=\"og:url\" content=\"https://tonapp.com/u/" + user.getName() + "\" />\n" +
            "    <!-- Twitter Card -->\n" +
            "    <meta name=\"twitter:card\" content=\"summary_large_image\" />\n" +
            "    <meta name=\"twitter:title\" content=\"" + propos.getTitre() + "\" />\n" +
            "    <meta name=\"twitter:description\" content=\"" + propos.getProfile() + "\" />\n" +
            "    <meta name=\"twitter:image\" content=\"" + photoUrl + "\" />\n" +
            "    <style> /* Styles ... */ /*</style>\n" +
            "</head>\n" +
            "<body> ... </body>\n" +
            "</html>";

    return ResponseEntity.ok().body(html);
}


        // 🔹 Ajouter
        @PostMapping("/add")
        public ResponseEntity<Object> createApropos(@PathVariable("slug") String slug,
                                                    @Valid @RequestBody Apropos apropos,
                                                    @RequestParam(name = "lang", required = false) String lang) {

            return buildResponse(
                    "apropos.create-success" , aproposService.createApropos(apropos)  ,lang,
                    HttpStatus.OK

            );
        }
*/