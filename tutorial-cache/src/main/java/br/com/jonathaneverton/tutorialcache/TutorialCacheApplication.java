package br.com.jonathaneverton.tutorialcache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableCaching
public class TutorialCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialCacheApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(ProdutoService produtoService) {
		return args -> {
			System.out.println("ID 1" + produtoService.getByID(1L));
			System.out.println("ID 2" + produtoService.getByID(2L));
			System.out.println("ID 3" + produtoService.getByID(3L));
			System.out.println("ID 3" + produtoService.getByID(3L));
			System.out.println("ID 3" + produtoService.getByID(3L));
			System.out.println("ID 3" + produtoService.getByID(3L));
			
		};
	}

}

record Produto(Long id, String nome) { }

@Service
class ProdutoService {
	Map<Long, Produto> produtos = new HashMap<>() {
		{
			put(1L, new Produto(1L, "Notebook 1"));
        	put(2L, new Produto(2L, "Notebook 2"));
        	put(3L, new Produto(3L, "Notebook 3"));
		}
    };

	@Cacheable("produtos")
	Produto getByID(Long id) {
		System.out.println("Procurando produto...");
		latencia();
		return produtos.get(id);
	}

	private void latencia() {
		try {
            long time = 5000L;
			Thread.sleep(time);
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalStateException(e);
        }
	}

}