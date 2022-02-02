import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';
import {Container,Row,Col} from 'react-bootstrap';
import Missing from "../images/missing game image.jpg"
import {Card,Badge,Button,Nav,Navbar} from 'react-bootstrap';
import {

    Link
      } from "react-router-dom";


class Recommendations extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
        currentUser:undefined,
        games:[]
 
    
        
};
componentDidMount(){

    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }

    axios.get("http://localhost:8080/userlist/recommendations/"+user.id)
    .then(response=>response.data)
    .then((data)=>{
        this.setState({games:data})
    });


}




goToDetails(id){
    this.props.history.push(`/gameDetails/${id}`);
    }




    render(){


        return(



            <Container>


            <Row>
            
            
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


                {this.state.games.map(currentGames =>(
                <Col xs={3} className="mb-5" key ={`${currentGames.id}`}>
                <Card className ="h-100 shadow-sm bg-white rounded">
                <Card.Img variant="top" src={currentGames.photoURL}onError={(e) => {
                                e.target.src = Missing // some replacement image
                                }} variant="top"   height="300px" />
                <Card.Body className ="d-flex flex-column">
                    <div className ="d-flex mb-2 justify-context-between">
                        <Card.Title className ="mb-0 font-weight-bold">{currentGames.title}</Card.Title>
                        <Badge pill className ="mb-1" bg="success">{currentGames.score}</Badge>
                    </div>
                    <Button className="mt-auto font-weight-bold"
                            variant ="secondary"
                            onClick= { () => this.goToDetails(currentGames.id)}
                    >See Details
                    </Button>
                    
                    {currentGames.review}
                </Card.Body>
                </Card>
                </Col>
                ))}
            </Row>
        </Container>



        )
    }
}
export default Recommendations;