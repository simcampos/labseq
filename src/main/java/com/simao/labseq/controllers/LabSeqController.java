package com.simao.labseq.controllers;

import com.simao.labseq.dtos.CalculationResponse;
import com.simao.labseq.service.CalculationService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.time.StopWatch;
import org.jboss.logging.Logger;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * LabSeqController is a RESTful controller that handles requests for calculating sequence values.
 * It provides an endpoint to calculate the value of a sequence based on an integer input 'n'.
 * The calculation is performed by the CalculationService, and the result is returned in JSON format.
 */
@Path("/labseq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LabSeqController {
    private final CalculationService calculationService;
    private static final Logger LOGGER = Logger.getLogger(LabSeqController.class);

    public LabSeqController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    /**
     * Calculates the sequence value for the given integer 'n'.
     * The sequence is defined such that:
     * - For n = 0, result is 0
     * - For n = 1, result is 1
     * - For n = 2, result is 0
     * - For n = 3, result is 1
     * - For n >= 4, result is the sum of the values at positions (n-4) and (n-3).
     *
     * @param n the input integer for which to calculate the sequence value.
     * @return a Response containing the calculated value in JSON format.
     */
    @GET
    @Path("/{n}")
    public Response calculateNumber(@PathParam("n") int n) {
        if (n < 0) {
            LOGGER.error("Input 'n' must be >= 0 and the provided was negative: " + n);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Input 'n' must not be negative. Provided: " + n)
                    .build();
        }

        LOGGER.info("Started calculation for " + n);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        BigInteger result = calculationService.calculateSequenceValue(n);
        stopWatch.stop();
        LOGGER.info("Calculation for number " + n + " execution successful in " + stopWatch.getTime(TimeUnit.MICROSECONDS) / 1000.0 + " milliseconds.");
        return Response.ok(new CalculationResponse(result.toString())).build();
    }
}