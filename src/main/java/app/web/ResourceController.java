package app.web;

import app.model.UserSpringWrapper;
import app.service.UserService;
import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.RelatedInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/info")
public class ResourceController {
    @Autowired
    UserService userService;

    @PostMapping("/titles")
    ResponseEntity<InfoToUpdateTitlesDTO> infoTitles(@AuthenticationPrincipal UserSpringWrapper user) {
        return ResponseEntity.ok(userService.retrieveInfoToUpdateTitles(user.getNested()));
    }

    @PostMapping("/data")
    ResponseEntity<RelatedInfoDTO> infoTitles(@RequestBody InfoToUpdateTitlesDTO info) {
        return ResponseEntity.ok(userService.retrieveRelevantInfo(info));
    }
}
