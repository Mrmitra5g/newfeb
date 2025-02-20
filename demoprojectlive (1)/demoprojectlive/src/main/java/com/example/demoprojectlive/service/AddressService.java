package com.example.demoprojectlive.service;

import com.example.demoprojectlive.repository.OrganizationRepository;
import com.example.demoprojectlive.repository.UserLookupRepository;
import com.example.demoprojectlive.repository.UserRepository1;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


@Service
public class AddressService {

    @Value("${smarty.api.key}")
    private String smartyApiKey;

    @Value("${lookup.limit.per.day}")
    private int lookupLimitPerDay; // The lookup limit can be configured

    private final RestTemplate restTemplate;
    private final OrganizationRepository orgRepo;
    private final UserRepository1 userRepo;
    private final UserLookupRepository userLookupRepository;
   



    public AddressService(RestTemplate restTemplate, OrganizationRepository orgRepo, UserRepository1 userRepo, UserLookupRepository userLookupRepository) {
        this.restTemplate = restTemplate;
        this.orgRepo = orgRepo;

        this.userRepo = userRepo;

        this.userLookupRepository = userLookupRepository;
    }

    // Address Lookup API
    public ResponseEntity<Object> addressLookup(String orgId, String userId, String search) {

/*        // Check if organization and user are not deleted
        if (!orgRepo.existsByOrgIdAndIsDeletedFalse(orgId)) {
            return new ResponseEntity<>("Organization is deleted", HttpStatus.BAD_REQUEST);
        }

        if (!userRepo.existsByUserIdAndIsDeletedFalse(userId)) {
            return new ResponseEntity<>("User is deleted", HttpStatus.BAD_REQUEST);
        }*/
//        String url = "https://us-autocomplete-pro.api.smarty.com/lookup";
//        Map<String, String> params = new HashMap<>();
////        params.put("org_id", orgId);
////        params.put("user_id", userId);
//
//        params.put("search", "47+W+13th+St+Apt");
//        params.put("source", "all ");
//        params.put("agent", "smarty+(sdk:javascript%405.1.3)"); // Fix formatting
//        params.put("key", "21102174564513388");
//
//        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", "Bearer " + smartyApiKey);
//        headers.set("Content-Type", "application/json");
//        headers.set("origin", "https://www.smarty.com");
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);


// Step 1: Ensure the organization and user are not deleted

// Check if the organization exists and is not deleted
        if (!orgRepo.existsByOrgIdAndIsDeletedFalse(Long.valueOf(orgId))) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Organization is deleted or does not exist");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

// Step 1: Check if the user exists and is not deleted
        if (!userRepo.existsByUserIdAndIsDeletedFalse(Long.valueOf(userId))) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "User is deleted or does not exist");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }


// Step 2: Check Lookup Limits (for example, assuming 100 requests per day)
        if (hasExceededLookupLimit(userId)) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Lookup limit exceeded for the user");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }





        try {

            RestTemplate restTemplate = new RestTemplate();

            // Set up the headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json, text/plain, */*");
            headers.set("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
            headers.set("Cache-Control", "no-cache");
            headers.set("Origin", "https://www.smarty.com");
            headers.set("Pragma", "no-cache");
            headers.set("Priority", "u=1, i");
            headers.set("Referer", "https://www.smarty.com/");
            headers.set("Sec-CH-UA", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
            headers.set("Sec-CH-UA-Mobile", "?0");
            headers.set("Sec-CH-UA-Platform", "\"macOS\"");
            headers.set("Sec-Fetch-Dest", "empty");
            headers.set("Sec-Fetch-Mode", "cors");
            headers.set("Sec-Fetch-Site", "same-site");
            headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

            // Define the URL and query parameters
            String url = "https://us-autocomplete-pro.api.smarty.com/lookup";
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("search", search)
                    .queryParam("include_only_cities", "")
                    .queryParam("include_only_states", "")
                    .queryParam("include_only_zip_codes", "")
                    .queryParam("exclude_states", "")
                    .queryParam("prefer_cities", "")
                    .queryParam("prefer_states", "")
                    .queryParam("prefer_zip_codes", "")
                    .queryParam("source", "all")
                    .queryParam("agent", "smarty+(sdk:javascript%405.1.3)")
                    .queryParam("key", "21102174564513");

            // Create a request entity with the URL, headers, and method type
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            // Perform the GET request
            ResponseEntity<String> response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // Print the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return ResponseEntity.ok(jsonNode);
        } catch (HttpStatusCodeException e) {
            return handleHttpError(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to check if the user has exceeded their lookup limit
    private boolean hasExceededLookupLimit(String userId) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the start of the day (midnight)
        LocalDateTime startOfDay = now.truncatedTo(ChronoUnit.DAYS);

        // Fetch the lookups for the user today
        long lookupCount = userLookupRepository.findByUserUserIdAndLookupTimestampAfter(Long.valueOf(userId), startOfDay).size();

        // Check if the user has exceeded the limit
        return lookupCount >= lookupLimitPerDay;
    }

/*    // Address Validation API
    public ResponseEntity<Object> validateAddress(String orgId, String userId, String street) {
        String url = "https://us-street.api.smarty.com/street-address";
        Map<String, String> params = new HashMap<>();
       *//* params.put("org_id", orgId);
        params.put("user_id", userId);*//*
        params.put("street", "16701+SW+72n\n" +
                "d+Ave+Palmetto+Bay+FL+33157");

//

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + smartyApiKey);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response);
        } catch (HttpStatusCodeException e) {
            return handleHttpError(e);
        }
    }*/

    // Address Validation API
    public ResponseEntity<Object> validateAddress(String orgId, String userId, String street) {


// Step 1: Ensure the organization and user are not deleted
        if (!orgRepo.existsByOrgIdAndIsDeletedFalse(Long.valueOf(orgId))) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Organization is deleted or does not exist");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }


        // Step 1: Check if the user exists and is not deleted
        if (!userRepo.existsByUserIdAndIsDeletedFalse(Long.valueOf(userId))) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User is deleted or does not exist");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

// Step 2: Check Lookup Limits (for example, assuming 100 requests per day)
        if (hasExceededLookupLimit(userId)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Lookup limit exceeded for the user");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }








        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        headers.set("Cache-Control", "no-cache");
        headers.set("Origin", "https://www.smarty.com");
        headers.set("Pragma", "no-cache");
        headers.set("Priority", "u=1, i");
        headers.set("Referer", "https://www.smarty.com/");
        headers.set("Sec-CH-UA", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.set("Sec-CH-UA-Mobile", "?0");
        headers.set("Sec-CH-UA-Platform", "\"macOS\"");
        headers.set("Sec-Fetch-Dest", "empty");
        headers.set("Sec-Fetch-Mode", "cors");
        System.out.println(" i am iom" +
                "");
        headers.set("Sec-Fetch-Site", "same-site");
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        // Define the URL and query parameters
        String url = "https://us-street.api.smarty.com/street-address";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", "21102174564513388")
                .queryParam("agent", "smarty+(website:demo%2Fsingle-address%40latest)")
                .queryParam("match", "enhanced")
                .queryParam("candidates", "5")

                .queryParam("geocode", "true")
                .queryParam("license", "us-rooftop-geocoding-cloud")
                .queryParam("street", "16701+SW+72n\n" +
                        "d+Ave+Palmetto+Bay+FL+33157");  // street parameter passed here

        // Log the URL for debugging
        System.out.println("Request URL: " + uriBuilder.toUriString());

        // Create a request entity with the URL, headers, and method type
      //  HttpEntity<String> entity = new HttpEntity<>(headers);
        org.springframework.http.HttpEntity<String> entity1 = new org.springframework.http.HttpEntity<>(headers);

        try {
            // Perform the GET request
            ResponseEntity<String> response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity1,
                    String.class
            );

            // Print the response
            System.out.println("API Response: " + response.getBody());

            // If response is empty, log the message
            if (response.getBody() != null && response.getBody().equals("[]")) {
                System.out.println("No data returned from the API.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return ResponseEntity.ok(jsonNode);
        } catch (HttpStatusCodeException e) {
            return handleHttpError(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /*// Handle HTTP Error Responses
    private ResponseEntity<Object> handleHttpError(HttpStatusCodeException e) {
        HttpStatus status = (HttpStatus) e.getStatusCode();
        String errorMessage = "An unexpected error occurred";

        switch (status) {
            case UNAUTHORIZED:
                errorMessage = "Unauthorized access. Please check your API key.";
                break;
            case NOT_FOUND:
                errorMessage = "The requested resource could not be found.";
                break;
            case TOO_MANY_REQUESTS:
                errorMessage = "Rate limit exceeded. Please try again later.";
                break;
            case INTERNAL_SERVER_ERROR:
                errorMessage = "Internal server error. Please try again later.";
                break;
            case BAD_REQUEST:
                errorMessage = "Bad request. Please check the parameters.";
                break;
            default:
                errorMessage = e.getResponseBodyAsString(); // Include the response body if any
                break;
        }

        return new ResponseEntity<>(errorMessage, status);
    }*/

    private ResponseEntity<Object> handleHttpError(HttpStatusCodeException e) {
        HttpStatus status = (HttpStatus) e.getStatusCode();
        String errorMessage = "An unexpected error occurred";

        // Error response body structure (initial part)
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error"); // Include the status as "error"

        switch (status) {
            case UNAUTHORIZED:
                // 401 Unauthorized
                errorMessage = "Smarty Streets authentication failed. Verify API key and credentials.";
                break;
            case FORBIDDEN:
                // 403 Forbidden
                errorMessage = "Organization or user is deleted.";
                break;
            case NOT_FOUND:
                // 404 Not Found
                errorMessage = "No matching address found. Verify the input address and try again.";
                break;
            case TOO_MANY_REQUESTS:
                // 429 Too Many Requests
                errorMessage = "Lookup limit exceeded. Try again later.";
                break;
            case INTERNAL_SERVER_ERROR:
                // 500 Internal Server Error
                errorMessage = "Unable to process the request. Please try again later.";
                break;
            case BAD_GATEWAY:
                // 502 Bad Gateway
                errorMessage = "Third-party service error: Unable to connect to Smarty Streets.";
                break;
            case GATEWAY_TIMEOUT:
                // 504 Gateway Timeout
                errorMessage = "Third-party service error: Request to SmartyStreets timed out.";
                break;
            default:
                // For other status codes, check if the response body has a useful message
                String responseBody = e.getResponseBodyAsString();
                // You can either set a default message or return the response body itself.
                // If the response body doesn't give useful information, you can default to a predefined message.
                if (responseBody.isEmpty() || responseBody.equals("null")) {
                    errorMessage = "An unexpected error occurred. Please try again later.";
                } else {
                    errorMessage = responseBody;  // If there is a useful error message, use it.
                }
                break;
        }

        // Set the message in the response map
        errorResponse.put("message", errorMessage);

        // Return the error response as a structured JSON with the HTTP status code
        return new ResponseEntity<>(errorResponse, status);
    }


}
