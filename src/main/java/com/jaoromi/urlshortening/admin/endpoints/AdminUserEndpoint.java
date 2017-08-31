package com.jaoromi.urlshortening.admin.endpoints;

import com.jaoromi.urlshortening.admin.dto.AdminUserDTO;
import com.jaoromi.urlshortening.admin.services.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class AdminUserEndpoint {

    @Inject
    private AdminUserService service;

    /**
     * 관리자 계정을 등록합니다. 마스터 권한에서만 수행 가능 합니다.
     * @param adminUser 등록할 관리자 계정 정보
     * @return 등록된 관리자 계정 정보
     */
    @RequestMapping(
            value = {"/admin/users"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    public ResponseEntity<Void> register(@RequestBody AdminUserDTO adminUser) {

        AdminUserDTO saved = service.register(adminUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    /**
     * 로그인된 관리자 계정 정보를 조회합니다.
     * @param id 관리자 계정 ID
     * @return ID 에 해당하는 관리자 계정 정보
     */
    @RequestMapping(
            value = {"/admin/users/{id}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    @ResponseBody
    public AdminUserDTO getAdminUser(@PathVariable("id") String id) {
        return service.getAdminUser(id);
    }

    /**
     * 로그인된 관리자 계정의 정보를 변경합니다.
     * @param id 변경할 관리자 계정 ID
     * @param adminUser 변경할 관리자 계정 정보
     * @return 변경된 관리자 계정 정보
     */
    @RequestMapping(
            value = {"/admin/users/{id}"},
            method = {RequestMethod.PUT},
            produces = {"application/json"}
    )
    @ResponseBody
    public AdminUserDTO updateAdminUser(
            @PathVariable("id") String id,
            @RequestBody AdminUserDTO adminUser) {
        adminUser.setId(id);

        return service.update(adminUser);
    }

    /**
     * 관리자 계정 정보를 삭제합니다. 마스터 권한에서만 수행 가능 합니다.
     * @param id 삭제할 관리자 계정 ID
     * @return
     */
    @RequestMapping(
            value = {"/admin/users/{id}"},
            method = {RequestMethod.DELETE},
            produces = {"application/json"}
    )
    public ResponseEntity<Void> deleteAdminUser(@PathVariable("id") String id) {
        service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
