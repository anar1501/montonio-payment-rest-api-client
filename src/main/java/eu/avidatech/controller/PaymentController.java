package eu.avidatech.controller;

import eu.avidatech.payload.BankList;
import eu.avidatech.payload.Payment;
import eu.avidatech.service.PaymentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static eu.avidatech.util.StringUtil.parseString;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
@Api(tags = "Payment Processing RESTful Services", value = "PaymentController", description = "Controller for Payment Service")
public class PaymentController {

    private final PaymentService paymentService;
    private final Logger logger;

    @ApiOperation(value = "Obtaining Redirect Url")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful code"),
                    @ApiResponse(code = 400, message = "Bad Request")
            }
    )
    @PostMapping
    public ResponseEntity<String> getRedirectUrl(@ApiParam(
            "To obtaing Redirect Url using This Web Service"
    ) @RequestBody Payment payment) {
        logger.info(parseString(payment));
        return ResponseEntity.ok(paymentService.getRedirectUrl(payment));
    }

    @ApiOperation(value = "Displaying the List of Available Banks")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful code"),
                    @ApiResponse(code = 404, message = "Not Found")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankList> findAllAvailableBank() {
        return ResponseEntity.ok(paymentService.findAllAvailableBank());
    }

    @ApiOperation(value = "Obtaining Payment Token From the Endpoint")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful code"),
                    @ApiResponse(code = 404, message = "Not Found")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "payment-token")
    public ResponseEntity<String> showPaymentToken() {
        return ResponseEntity.ok(paymentService.getPaymentTokenFromTheUrl());
    }

}
