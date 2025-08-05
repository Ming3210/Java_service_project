package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.PasswordChangeRequest;
import ra.java_service_project.model.dto.request.UserPagingRequest;
import ra.java_service_project.model.dto.request.UserProfileRequest;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<APIDataResponse<Page<User>>> getAllUsers(@RequestBody UserPagingRequest userPagingRequest) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.getAllUsers(userPagingRequest.getPage(), userPagingRequest.getItemPage(), userPagingRequest.getSortBy(), userPagingRequest.getOrderBy()), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<APIDataResponse<User>> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.findById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<User>> createUser(@RequestBody User user) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.createUser(user), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<APIDataResponse<User>> updateUser(@RequestBody User user, @PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.updateUser(user, id), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("{id}/password")
    public ResponseEntity<APIDataResponse<Boolean>> updatePassword(@RequestBody PasswordChangeRequest request, @PathVariable Integer id) {
        return new ResponseEntity<>(
                new APIDataResponse<>(
                        true,
                        "success",
                        userService.updatePassword(request.getNewPassword(), id),
                        HttpStatus.OK
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(params = "status")
    public ResponseEntity<APIDataResponse<List<UserProfileRequest>>> getUserByStatus(@RequestParam Boolean status) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.getUsersByStatus(status), HttpStatus.OK), HttpStatus.OK);
    }
}
