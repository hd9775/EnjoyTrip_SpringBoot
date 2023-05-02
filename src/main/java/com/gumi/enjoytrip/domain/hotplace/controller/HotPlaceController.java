package com.gumi.enjoytrip.domain.hotplace.controller;

import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hot-places")
@RequiredArgsConstructor
public class HotPlaceController {

    private final UserService userService;

    @GetMapping("/")
    public String getHotPlaces() {
        User user = userService.getLoginUser();
        return "hot-places";
    }
}
