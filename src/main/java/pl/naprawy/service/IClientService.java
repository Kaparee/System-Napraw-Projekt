package pl.naprawy.service;

import pl.naprawy.model.Client;

public interface IClientService {
    Client getClientByLogin(String login);
}
