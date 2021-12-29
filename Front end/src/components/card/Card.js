import React from 'react';
import {

    Link
      } from "react-router-dom";
    

      
function Card({title,photoURL,genre1,genre2,genre3,genre4,score,id}){
return(
    <div className ="card-container">
        <div className = "delete-button">
        <button id = "delete-button" onClick={this.deleteGame.bind(id)}>Delete</button>
        </div>
        <div className="card-score">{score}</div>
        <div className="card-image">
            <img src={photoURL}/>
        </div>
        <div className="card-title">
        <li><Link className ="navbar_links" to= {"gameDetails/"+id}>{title} </Link></li>
        </div>
    </div>
)

}
export default Card;