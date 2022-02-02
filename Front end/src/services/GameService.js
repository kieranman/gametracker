import axios from "axios";

const API_URL = "http://localhost:8080/userlist/add/";

class GameService {



  addToList(rating,review,status,gameId,token) {
    return axios.post(API_URL + token, {
        rating,
        review,
        status,
        gameId
    });
  }


  




}

export default new GameService();