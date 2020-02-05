package guru.springframework.miscbrewery.web.controller;

import org.springframework.web.bind.annotation.RestController;
import guru.springframework.miscbrewery.services.BeerService;
import guru.springframework.miscbrewery.web.model.BeerDto;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {
	
	private final BeerService beerService;
	
	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}
	
	@GetMapping({"/{beerId}"})
	public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
		return new ResponseEntity<>(beerService.getBeerById(beerId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<BeerDto> handlePost(@Valid @RequestBody BeerDto beerDto) {
		BeerDto savedDto = beerService.saveNewBeer(beerDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location","/api/v1/beer" + savedDto.getId().toString());
		return new ResponseEntity<BeerDto>(headers,HttpStatus.CREATED);
	}
	
	@PutMapping({"/{beerId}"})
	public ResponseEntity handleUpdate(@PathVariable UUID beerId,@Valid @RequestBody BeerDto beerDto) {
		beerService.updateBeer(beerId,beerDto);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping({"/{beerId}"})
	public void handleDelete(@PathVariable UUID beerId) {
		beerService.deleteBeer(beerId);
	}

}
