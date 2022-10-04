package com.nalstudio.springjms;

import com.nalstudio.springjms.data.IngredientRepository;
import com.nalstudio.springjms.domain.Ingredient;
import com.nalstudio.springjms.domain.Ingredient.Type;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringJmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	// 부트스트랩 클래스를 변경
	// 애플리케이션이 시작되면서 호출되는 dataLoader()에서 식자재 데이터를 미리 저장하기위해
	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
				repository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
				repository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
				repository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
				repository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
				repository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
				repository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
				repository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
				repository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
			}
		};
	}

}
