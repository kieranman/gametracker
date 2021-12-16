import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';

import {

    Link
      } from "react-router-dom";


class UserList extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
        currentUser:undefined,
        currentPage:1,
        gamesPerPage:3,
        games:[]
 
    
        
};
componentDidMount(){

    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }



        axios.get("http://localhost:8080/userlist/"+user.accessToken)
            .then(response=>response.data)
            .then((data)=>{
                this.setState({games:data})
            });

}


nextPage =() =>{
    if (this.state.currentPage < Math.ceil(this.state.games.length/this.state.gamesPerPage)){
        this.setState({
            currentPage:this.state.currentPage+1
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



changePage = event => {
    this.setState({
        [event.target.name]: parseInt(event.target.value)
    });
};







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
          <th>Status</th>
          <th>Review</th>
          </tr>
      </thead>
      <tbody>
      {this.state.games.length===0 ?
          <tr>
              <td colSpan="4" > No Games Avalaiable</td>
          </tr>: currentGames.map((userlist,game,index)=>(
              <tr key={index}>

                  
                 <td id = "image-Header"> <img src={userlist.game.photoURL} roundedCircle width="100px" height="100px"/></td>
                  <td id = "title-Header"><li><Link className ="navbar_links" to= {"gameDetails/"+userlist.game.id}>{userlist.game.title} </Link></li></td>
                  <td id = "rating-Header">{userlist.rating}</td>
                  <td id = "status-Header">{userlist.status}</td>
                  <td id = "review-Header">{userlist.review}</td>
                  






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
export default UserList;