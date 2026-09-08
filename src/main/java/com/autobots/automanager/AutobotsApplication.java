package com.autobots.automanager;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.autobots.automanager.cliente.domain.Cliente;
import com.autobots.automanager.cliente.domain.Endereco;
import com.autobots.automanager.cliente.domain.Telefone;
import com.autobots.automanager.cliente.repositories.ClienteRepositorio;
import com.autobots.automanager.documento.domain.Documento;

@SpringBootApplication
public class AutobotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutobotsApplication.class, args);
	}

	@Component
	public static class Runner implements ApplicationRunner {
		@Autowired
		public ClienteRepositorio repositorio;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			Calendar calendario = Calendar.getInstance();
			calendario.set(2002, 05, 15);

			Cliente cliente = new Cliente();
			cliente.setNome("Pedro Alcântara de Bragança e Bourbon");
			cliente.setDataCadastro(Calendar.getInstance().getTime());
			cliente.setDataNascimento(calendario.getTime());
			cliente.setNomeSocial("Dom Pedro");

			Telefone telefone = new Telefone();
			telefone.setDdd("21");
			telefone.setNumero("981234576");
			cliente.getTelefones().add(telefone);

			Endereco endereco = new Endereco();
			endereco.setEstado("Rio de Janeiro");
			endereco.setCidade("Rio de Janeiro");
			endereco.setBairro("Copacabana");
			endereco.setRua("Avenida Atlântica");
			endereco.setNumero("1702");
			endereco.setCodigoPostal("22021001");
			endereco.setInformacoesAdicionais("Hotel Copacabana palace");
			cliente.setEndereco(endereco);

			Documento rg = new Documento();
			rg.setTipo("RG");
			rg.setNumero("1500");

			Documento cpf = new Documento();
			cpf.setTipo("RG");
			cpf.setNumero("00000000001");

			cliente.getDocumentos().add(rg);
			cliente.getDocumentos().add(cpf);

			repositorio.save(cliente);
		}
	}

}
