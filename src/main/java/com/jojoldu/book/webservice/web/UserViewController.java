package com.jojoldu.book.webservice.web;

import com.jojoldu.book.webservice.common.file.FileStore;
import com.jojoldu.book.webservice.config.auth.LoginUser;
import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.domain.userImage.UserImageRepository;
import com.jojoldu.book.webservice.service.file.FileService;
import com.jojoldu.book.webservice.service.users.UserService;
import com.jojoldu.book.webservice.web.dto.UserImageDto;
import com.nimbusds.oauth2.sdk.util.URLUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.net.URLEncoder;

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

        String encodedUploadFileName = UriUtils.encode()
        String contentDisposition = "attachment; filename=\"" + fileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
