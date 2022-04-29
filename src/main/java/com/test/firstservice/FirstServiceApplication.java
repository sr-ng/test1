package com.test.firstservice;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
//@RestController
public class FirstServiceApplication implements CommandLineRunner {

	@Value("${myApp.username:defaultvalue}") // via application property
	private String username;

	@Value("${myApp.password:defaultpassword}") // via application property
	private String password;

	@Value("${myApp.secret.password:defaultsecretpassword}") // via application property
	private String secretPassword;


	@Value("${myApp.secretVal:defaultsecret}")
	private String secret;

	public static void main(String[] args) {
		SpringApplication.run(FirstServiceApplication.class, args);
	}

	@Override
	public void run(String args[]) throws Exception
	{
		System.out.println("Username from environment variable : " + username);
		System.out.println("Password from environment variable : " + password);

		System.out.println("Secret Password from environment variable : " + secretPassword);

		System.out.println("Secret value from SecretManager: ------");
		//getConfig().forEach((k, v) -> System.out.println((k + " : " + v)));
	}


	public HashMap<String, Object> getConfig() {
		//String secret = System.getenv("CLOUD_SQL_CREDENTIALS_SECRET");
		if (secret == null) {
			throw new IllegalStateException("\"SECRET_VAL\" is required.");
		}
		try {
			HashMap<String, Object> config = new Gson().fromJson(secret, HashMap.class);
			return config;
		} catch (JsonSyntaxException e) {
			/*logger.error(
					"Unable to parse secret from Secret Manager. Make sure that it is JSON formatted: "
							+ e);*/
			throw new RuntimeException(
					"Unable to parse secret from Secret Manager. Make sure that it is JSON formatted.");
		}
	}
	/*@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}*/
}
