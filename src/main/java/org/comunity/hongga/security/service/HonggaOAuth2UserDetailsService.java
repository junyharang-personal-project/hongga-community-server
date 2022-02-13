package org.comunity.hongga.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

@Slf4j @RequiredArgsConstructor
@Service public class HonggaOAuth2UserDetailsService implements OAuth2UserService {
} // class 끝
