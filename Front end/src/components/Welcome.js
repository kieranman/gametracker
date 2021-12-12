import React from "react";
import {Container,Button} from 'react-bootstrap'; 
// import 'bootstrap/dist/css/bootstrap.css';
import './pages.css';
class Welcome extends React.Component{
    render(){
        return(

            <div className= "welcome-container" bg="dark">
            <h1 class="display-4">Welcome to game tracker!</h1>
            <p class="lead">Start by browsing some games</p>
 


            </div>
        );
    }
}

export default Welcome;