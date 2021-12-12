import React from 'react';
import axios from 'axios';
import './pages.css';

import {

    Link
      } from "react-router-dom";


class GameDetails extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
    id:'',title:'',genre1:'',genre2:'',genre3:'',genre4:'',photoURL:'',synopsis:''
    
};

    componentDidMount(){
        const gameId = +this.props.match.params.id;
        if(gameId){
            this.getGameById(gameId);
            
        }

    }


        



    getGameById= (gameId)=>{
        axios.get("http://localhost:8080/games/"+gameId)
        .then(response =>{
            if(response.data !=null){
                this.setState({
                    id:response.data.id,
                    title:response.data.title,
                    photoURL:response.data.photoURL,
                    synopsis:response.data.synopsis,
                    genre1:response.data.genre1,
                    genre2:response.data.genre2,
                    genre3:response.data.genre3,
                    genre4:response.data.genre4

                });
            }
        }).catch((error)=>{
            console.error("error - "+error)
        


        });
    }



    gameList=()=>{
        this.props.history.push('/list');
    }



    render(){
        const {title,photoURL,genre1,genre2,genre3,genre4,synopsis,id} = this.state;

        return(
            

            <div className ="gameDetails-form">
                <table className= "gameDetails-main">
                <td><div className ="gameDetails-imagebox">
                <label>{title}</label>
                    <img src={photoURL} />
                    
                </div>
                <br/>
                <label>Genres:   </label>
                <text>  {genre1} {genre2} {genre3} {genre4}</text><br/>
                <button id="addToList-button"> <Link to={"/addToList/"+id}>Add</Link></button>
                </td>
                <td><div className = "gameDetails-text">
                    <br/>
                    <label>Game Synopsis:   </label>
                    <br/>

                <text>{synopsis}</text> 
                
                </div></td>
                </table>
          </div>
        )
    }
}
export default GameDetails;