package pers.xue.skills.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.xue.skills.exception.ApiError;
import pers.xue.skills.remote.req.DateReqDTO;
import pers.xue.skills.remote.rsp.DateRspDTO;

import java.time.LocalDateTime;

/**
 * @auther huangzhixue
 * @data 2021/8/5 5:29 下午
 * @Description
 */
@RestController
public class SwaggerTestController {

    @PostMapping("/date1")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid request", response = ApiError.class)
    })
    public ResponseEntity<ApiError> date(@RequestBody DateReqDTO dateReqDTO) {
        ApiError apiError = new ApiError();
        apiError.setCode(200);
        apiError.setResponseDesc("错误");
        return new ResponseEntity<>(apiError, HttpStatus.OK);
    }
}
