package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.CoinRequest;
import com.app.entity.CoinResponse;
import com.app.entity.GamingMachine;
import com.app.repository.GamingMachineRepository;

@RestController
public class GamingMachineController {

	private final GamingMachineRepository gamingMachineRepository;

	public GamingMachineController(GamingMachineRepository gamingMachineRepository) {
		this.gamingMachineRepository = gamingMachineRepository;
	}

	@PostMapping("/enterCoins")
	public ResponseEntity<?> enterCoins(@RequestBody CoinRequest coinRequest) {
		int coinDenomination = coinRequest.getCoin();
		int numberOfCoins = coinRequest.getQty();
		double numberOfHours = coinRequest.getHours();
		String status = coinRequest.getStatus();

		// Validate coin denomination
		if (coinDenomination != 1 && coinDenomination != 2 && coinDenomination != 5 && coinDenomination != 10) {
			return ResponseEntity.badRequest()
					.body("Invalid coin denomination. Accepted denominations are 1, 2, 5, and 10.");
		}

		// Validate number of hours
		if (numberOfHours < 1) {
			return ResponseEntity.badRequest().body("Number of hours should be at least 1.");
		}

		// Calculate amount paid and valid till hours
		double amountPaid = coinDenomination * numberOfCoins;
		double validTill = numberOfHours + (amountPaid / 10);

		if (status.equalsIgnoreCase("START")) {
			String message = "Welcome to gaming, Your subscription is active till " + numberOfHours + "pm";
			String validTillMessage = "Valid till " + validTill + " hours.";
			double remainingChange = amountPaid - (numberOfHours * 10);

			// Save gaming machine data
			GamingMachine gamingMachine = new GamingMachine();
			gamingMachine.setCoin(coinDenomination);
			gamingMachine.setQty(numberOfCoins);
			gamingMachine.setHours(numberOfHours);
			gamingMachine.setStatus(status);
			gamingMachineRepository.save(gamingMachine);

			// Prepare response
			CoinResponse response = new CoinResponse(message, validTillMessage, remainingChange);
			response.setMessage(message);
			response.setValidTill(validTillMessage);
			return ResponseEntity.ok(response);
		} else if (status.equalsIgnoreCase("CANCEL")) {
			double refundAmount = (numberOfHours * 10) - amountPaid;

			// Save gaming machine data
			GamingMachine gamingMachine = new GamingMachine();
			gamingMachine.setCoin(coinDenomination);
			gamingMachine.setQty(numberOfCoins);
			gamingMachine.setHours(numberOfHours);
			gamingMachine.setStatus(status);
			gamingMachineRepository.save(gamingMachine);

			// Prepare response
			CoinResponse response = new CoinResponse(refundAmount);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body("Invalid status. Accepted statuses are 'START' and 'CANCEL'.");
		}
	}
}
