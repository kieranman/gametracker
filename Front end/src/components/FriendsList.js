import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';
import {Container,Row,Col} from 'react-bootstrap';

import {Card,Badge,Button,Nav,Navbar} from 'react-bootstrap';
import {

    Link
      } from "react-router-dom";


class FriendsList extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
        currentUser:undefined,
        friends:[]
 
    
        
};
componentDidMount(){

    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }



        axios.get("http://localhost:8080/getfriends/"+user.id)
            .then(response=>response.data)
            .then((data)=>{
                this.setState({friends:data})
            });

}


goToFriendsGameList(id){
    this.props.history.push(`/userlist/${id}`);
}
goToChat(id){
    this.props.history.push(`/chatroom/${id}`);
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

                {this.state.friends.map(friendsList =>(
                <Col xs={3} className="mb-5" key ={`${friendsList}`}>
                <Card className ="h-100 shadow-sm bg-white rounded">
                <Card.Img variant="top" src=""/>
                <Card.Body className ="d-flex flex-column">
                    <div className ="d-flex mb-2 justify-context-between">
                        <Card.Title className ="mb-0 font-weight-bold">{friendsList}</Card.Title>
                    </div>
                    <Button className="mt-auto font-weight-bold"
                            variant ="secondary"
                            onClick= { () => this.goToFriendsGameList(friendsList)}
                            
                    >
                        List
                    </Button> <br/>
                    <Button className="mt-auto font-weight-bold"
                            variant ="secondary"
                            onClick= { () => this.goToChat(friendsList.username)}
                    >Chat
                    </Button>
                </Card.Body>
                </Card>
                </Col>
                ))}
            </Row>
        </Container>



        )
    }
}
export default FriendsList;