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


class UserList extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;

    }

    initalState ={
        currentUser:undefined,
        currentPage:1,
        gamesPerPage:4,
        games:[],
        friendId:this.props.match.params.friendId
 
    
        
};
componentDidMount(){

    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
      });
    }


    if(this.state.friendId){
        axios.get("http://localhost:8080/userlist/"+this.state.friendId)
        .then(response=>response.data)
        .then((data)=>{
            this.setState({games:data})
        });
    }
    else{
        axios.get("http://localhost:8080/userlist/"+user.id)
        .then(response=>response.data)
        .then((data)=>{
            this.setState({games:data})
        });
    }
  

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


goToDetails(id){
    this.props.history.push(`/gameDetails/${id}`);
    }



    render(){
        const {games,currentPage,gamesPerPage} = this.state;
        const lastIndex = currentPage * gamesPerPage;
        const firstIndex = lastIndex-gamesPerPage;
        const currentGames = games.slice(firstIndex,lastIndex);

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
                <Card.Img variant="top" src={currentGames.game.photoURL} onError={(e) => {
                                e.target.src = Missing // some replacement image
                                }} variant="top"   height="300px" />
                <Card.Body className ="d-flex flex-column">
                    <div className ="d-flex mb-2 justify-context-between">
                        <Card.Title className ="mb-0 font-weight-bold">{currentGames.game.title}</Card.Title>
                        <Badge pill className ="mb-1" bg="success">{currentGames.rating}</Badge>
                        <Badge pill className = "mb-1" bg = "secondary">{currentGames.status}</Badge>
                    </div>
                    <Button className="mt-auto font-weight-bold"
                            variant ="secondary"
                            onClick= { () => this.goToDetails(currentGames.game.id)}
                    >See Details
                    </Button>
                    {currentGames.review}
                </Card.Body>
                </Card>
                </Col>
                ))}
            </Row>
        </Container>



    //         <div className ="gamelist-box">

    //         <table className ="gamelist-table" >
    //   <thead>
    //       <tr>

    //       <th>Image</th>
    //       <th>Title</th>
          
    //       <th>Score</th>
    //       <th>Status</th>
    //       <th>Review</th>
    //       </tr>
    //   </thead>
    //   <tbody>
    //   {this.state.games.length===0 ?
    //       <tr>
    //           <td colSpan="4" > No Games Avalaiable</td>
    //       </tr>: currentGames.map((userlist,index)=>(
    //           <tr key={index}>

                  
    //              <td id = "image-Header"> <img src={userlist.game.photoURL} roundedCircle width="100px" height="100px"/></td>
    //               <td id = "title-Header"><li><Link className ="navbar_links" to= {"gameDetails/"+userlist.game.id}>{userlist.game.title} </Link></li></td>
    //               <td ><div className = "rating-Header">{userlist.rating}</div></td>
    //               <td><div className = "status-Header">{userlist.status}</div></td>
    //               <td ><div className = "review-Header">{userlist.review}</div></td>
                  






    //           </tr>
    //       ))        
    //       }
    //   </tbody>
    //   </table>
    //   <div className = "button-holder" onChange = {this.changePage}>
      
    //   <br/>

    //   <div className = "arrow-container">
    //   <button  onClick={this.previousPage}>Back</button>
    //   <label>{currentPage}</label>
    //   <button  onClick={this.nextPage}>Next</button>
    //   </div>

    //   </div>
          
    // </div>
        )
    }
}
export default UserList;