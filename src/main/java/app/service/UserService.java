package app.service;

import app.model.dao.UserDAO;
import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.RelatedInfoDTO;

public interface UserService {
    UserDAO findByUsername(String email);

    InfoToUpdateTitlesDTO retrieveInfoToUpdateTitles(UserDAO user);

    RelatedInfoDTO retrieveRelevantInfo(InfoToUpdateTitlesDTO info);
}