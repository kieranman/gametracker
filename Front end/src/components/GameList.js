import React from 'react';
import {Form,Button,Card} from 'react-bootstrap';
import './pages.css';
import axios from 'axios';
import pokemonImage from './pokemon.jpg'
import {

    Link
      } from "react-router-dom";
    
    
class GameList extends React.Component{
    constructor(props){
        super(props);
        this.state ={
            games :[],
            currentPage:1,
            gamesPerPage:7
        };
    }

    componentDidMount(){
        axios.get("http://localhost:8080/games")
            .then(response=>response.data)
            .then((data)=>{
                this.setState({games:data})
            });
    }


    firstPage =()=>{
        if(this.state.currentPage>1){
            this.setState({
                currentPage:1
            });
        }
    };

    previousPage =() => {
        if(this.state.currentPage >1){
            this.setState({
                currentPage:this.state.currentPage-1
            });
        }
    };

    lastPage =() =>{
        if (this.state.currentPage < Math.ceil(this.state.games.length/this.state.gamesPerPage)){
            this.setState({
                currentPage:Math.ceil(this.state.games.length/this.state.gamesPerPage)
            });
        }
    };

    nextPage =() =>{
        if (this.state.currentPage < Math.ceil(this.state.games.length/this.state.gamesPerPage)){
            this.setState({
                currentPage:this.state.currentPage+1
            });
        }
    };




    changePage = event => {
        this.setState({
            [event.target.name]: parseInt(event.target.value)
        });
    };




    deleteGame =(gameId)=>{
       axios.delete("http://localhost:8080/games/"+gameId)
        .then(response=>{
            if(response.data !=null){
                this.setState({
                    games:this.state.games.filter(game=> game.id !==gameId)
                })
            }
        })
    }

    render(){
        const {games,currentPage,gamesPerPage} = this.state;
        const lastIndex = currentPage * gamesPerPage;
        const firstIndex = lastIndex-gamesPerPage;
        const currentGames = games.slice(firstIndex,lastIndex);



        return(
            <div className ="gamelist-box">

                  <table className ="gamelist-table" >
            <thead>
                <tr>

                <th>Image</th>
                <th>Title</th>
                
                <th>Score</th>
                <th>Your Score</th>
                <th>Status</th>
                </tr>
            </thead>
            <tbody>
            {this.state.games.length===0 ?
                <tr>
                    <td colSpan="4" > No Games Avalaiable</td>
                </tr>: currentGames.map((game,index)=>(
                    <tr key={index}>

                        
                       <td id = "image-Header"> <img src={game.photoURL} roundedCircle width="100px" height="100px"/></td>
                        <td><li><Link className ="navbar_links" to= {"gameDetails/"+game.id}>{game.title} </Link></li></td>
                        
                        <td></td>
                        <td></td>
                        <td></td>
                        

                        <button id="edit-button"> <Link to={"edit/"+game.id}>Edit</Link></button>
                        <button id = "delete-button" onClick={this.deleteGame.bind(this,game.id)}>Delete</button>





                    </tr>
                ))        
                }
            </tbody>
            </table>
            <div className = "button-holder" onChange = {this.changePage}>
            
            <br/>

            <div className = "arrow-container">
            <button  onClick={this.previousPage}>Back</button>
            <label>{currentPage}</label>
            <button  onClick={this.nextPage}>Next</button>
            </div>

            </div>
                
          </div>
        )
    }
}
export default GameList;