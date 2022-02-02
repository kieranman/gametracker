import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';

import {

    Link
      } from "react-router-dom";
import { Container,Navbar,Nav } from 'react-bootstrap';


class Profile extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
        currentUser:undefined,
        currentPage:1,
        gamesPerPage:4,
        games:[]
 
    
        
};
componentDidMount(){

    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }

}









    render(){

        return(
            <Container>
            <img className ="profile-image" src={"https://www.moodvisuals.com/wp-content/uploads/2019/02/GoW-Header-01.jpg"} >
            </img>
            <div className="profile-contents">
                <Navbar>
                <Nav className="me-auto">
                    <Nav.Link onClick={() => {window.location.href='/userlist'}}>My List</Nav.Link>
                    <Nav.Link onClick={() => {window.location.href='/recommendations'}}>Recommendations</Nav.Link>
                    <Nav.Link onClick={() => {window.location.href='/friends'}}>Friends</Nav.Link>
                    </Nav>
                </Navbar>
            </div>
            </Container>
        )
    }
}
export default Profile;