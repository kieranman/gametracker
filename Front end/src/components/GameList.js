import React from 'react';
import './pages.css';
import axios from 'axios';
import pokemonImage from './pokemon.jpg'
import bin from '../images/bin.png';
import edit from '../images/edit.png';
import GameService from '../services/GameService';

import {

    Link
      } from "react-router-dom";
import { Form } from 'react-bootstrap';
    
    
class GameList extends React.Component{
    constructor(props){
        super(props);
        this.onChangeSearch = this.onChangeSearch.bind(this);
        this.submitForm = this.submitForm.bind(this);
        this.state ={
            games :[],
            currentPage:1,
            gamesPerPage:4,
            search:this.props.match.params.search
        };
    }

    componentDidMount(){

        if(this.state.search){
            axios.get("http://localhost:8080/games/title/"+this.state.search)
            .then(response=>response.data)
            .then((data)=>{
                this.setState({games:data})
                
                
            });
        
        }
        else{
            axios.get("http://localhost:8080/games")
            .then(response=>response.data)
            .then((data)=>{
                this.setState({games:data})
            });
        }

        
        



    }


    onChangeSearch(e) {
        this.setState({
          search: e.target.value
        });
      }

      submitForm(e){
        e.preventDefault(); 
        this.props.history.push(`/list/${this.state.search}`);
        window.location.reload();

    };


    // firstPage =()=>{
    //     if(this.state.currentPage>1){
    //         this.setState({
    //             currentPage:1
    //         });
    //     }
    // };

    previousPage =() => {
        if(this.state.currentPage >1){
            this.setState({
                currentPage:this.state.currentPage-1
            });
        }
    };

    // lastPage =() =>{
    //     if (this.state.currentPage < Math.ceil(this.state.games.length/this.state.gamesPerPage)){
    //         this.setState({
    //             currentPage:Math.ceil(this.state.games.length/this.state.gamesPerPage)
    //         });
    //     }
    // };

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
{/* {this.state.games.map(
    game =>  

    <div className ="card-container">
        <button id = "edit-button"> <Link to={"edit/"+game.id}><img src={edit} id ="edit-image"></img></Link></button>
        <div className="card-score">{game.score}
        <button id = "delete-button" onClick={this.deleteGame.bind(this,game.id)}>Delete</button>
        </div>
        <div className="card-image">
            <img src={game.photoURL}/>
        </div>
        <div className="card-title">
        <li><Link className ="navbar_links" to= {"gameDetails/"+game.id}>{game.title} </Link></li>
        </div>
    </div>

           
)
} */}

    <form onSubmit={this.submitForm}>
           <input id="search-bar"  type="text" placeholder="Search.." name="search"
           value={this.state.search} onChange={this.onChangeSearch}
           />
           <input id="search-submit" type="submit"  value="Submit"/>
    </form>


                  <table className ="gamelist-table" >
            <thead>
                <tr>

                <th></th>
                <th></th>
                <th></th>
                


                </tr>
            </thead>
            <tbody>
            {this.state.games.length===0 ?
                <tr>
                    <td colSpan="4" > No Games Avalaiable</td>
                </tr>: currentGames.map((game,index)=>(
                    <tr key={index}>

                        
                       <td id = "image-Header"> <img src={game.photoURL} roundedCircle width="100px" height="100px"/></td>
                       <td><div className = "score-box">{game.score}</div></td>
                        <td>
                            <li><Link className ="navbar_links" to= {"gameDetails/"+game.id}>{game.title}</Link></li>
                            
                            </td>
                      
                        
                        

                        <button id = "edit-button"> <Link to={"edit/"+game.id}><img src={edit} id ="edit-image"></img></Link></button>
                        <button id = "delete-button" onClick={this.deleteGame.bind(this,game.id)}><img src ={bin} id ="delete-image"/></button>






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