package eu.avidatech.controller;

import eu.avidatech.payload.BankList;
import eu.avidatech.payload.Decoded;
import eu.avidatech.payload.Payment;
import eu.avidatech.service.PaymentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static eu.avidatech.util.StringUtil.parseString;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "montonio/payment")
@Api(tags = "Payment Processing RESTful Services", value = "PaymentController", description = "Controller for Payment Service")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
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
            "To obtaining Redirect Url using This Web Service"
    ) @RequestBody Payment payment) {
        logger.info(parseString(payment));
        return ResponseEntity.ok(paymentService.getRedirectUrl(payment));
    }


    @ApiOperation(value = "Validating the Payment")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful code"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    @GetMapping(path = "validate-payment")
    public String validatePayment(@RequestParam(name = "payment_token") String paymentToken) {
        return paymentService.validatePayment(paymentToken);
    }


    @ApiOperation(value = "Webhook Notification")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful code"),
                    @ApiResponse(code = 500, message = "Internal Server Error")
            }
    )
    @PostMapping(path = "webhook-notification")
    public String getWebhookNotificationUrl(@RequestBody String paymentToken) {
        return paymentService.getWebhookNotificationUrl(paymentToken);
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
