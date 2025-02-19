package io.github.leolimaferreira.gerenciamento_despesas_pessoais.service;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Client;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public void salvar(Client client) {
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        clientRepository.save(client);
    }

    public Client obterPorClientID(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
