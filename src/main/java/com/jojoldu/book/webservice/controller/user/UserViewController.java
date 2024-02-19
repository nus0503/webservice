package com.jojoldu.book.webservice.controller.user;

import com.jojoldu.book.webservice.common.file.FileStore;
import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.controller.user.dto.TempPasswordFormRequestDto;
import com.jojoldu.book.webservice.service.file.FileService;
import com.jojoldu.book.webservice.service.users.UserService;
import com.jojoldu.book.webservice.controller.user.dto.UserImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    private final FileStore fileStore;

    private final FileService fileService;

    @GetMapping("/loginForm")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "find-password";
    }

    @PostMapping("/findPassword")
    public String sendTempPassword(@ModelAttribute @Valid TempPasswordFormRequestDto dto, Errors errors, Model model) throws MessagingException, UnsupportedEncodingException {
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getFieldValue("email"));
            return "find-password";
        }
        userService.modifyAsPassword(dto.getEmail());
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modify(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);

            model.addAttribute("image", userService.findById(user.getEmail()));
        }
        return "user-modify";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        UserImageDto file = fileService.getFile(itemId);
        String fileName = file.getFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(fileName));

        log.info("uploadFileName={}", fileName);

        String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
