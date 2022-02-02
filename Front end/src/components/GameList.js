import React from 'react';
import './pages.css';
import axios from 'axios';

import AuthService from "../services/AuthService"
import 'bootstrap/dist/css/bootstrap.min.css'
import {Container,Row,Col} from 'react-bootstrap';
import Missing from "../images/missing game image.jpg"
import {Card,Badge,Button,Nav,Navbar} from 'react-bootstrap';
import {

    Link
      } from "react-router-dom";

    
    
class GameList extends React.Component{

    
    constructor(props){
        super(props);
        this.onChangeSearch = this.onChangeSearch.bind(this);
        this.submitForm = this.submitForm.bind(this);
        this.goToDetails = this.goToDetails.bind(this);



        this.state ={
            showAdminBoard: false,
            currentUser: undefined,
            games :[],
            currentPage:1,
            gamesPerPage:4,
            search:this.props.match.params.search,
            genre:this.props.match.params.genre

        };

        function selectGenre(e) {
            const genre = e.target.value;
            this.setState({
                genre:genre
            })
        
          }

    }
    


    componentDidMount(){
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
              currentUser: user,
              showAdminBoard: user.roles.includes("ROLE_ADMIN"),
            });
          }
        if(this.state.search){
            axios.get("http://localhost:8080/games/title/"+this.state.search)
            .then(response=>response.data)
            .then((data)=>{
                this.setState({games:data})
                
                
            });
        
        }
        else if(this.state.genre){
            axios.get("http://localhost:8080/games/genre/"+this.state.genre)
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

    onError = () => {
        if (!this.state.errored) {
          this.setState({
            src: Missing,
            errored: true,
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









    previousPage =() => {
        if(this.state.currentPage >1){
            this.setState({
                currentPage:this.state.currentPage-1
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


    goToDetails(id){
        this.props.history.push(`/gameDetails/${id}`);
        }
    gotToUpdateDetails(id){
        this.props.history.push(`/edit/${id}`);
    }



    render(){




        return(


            <Container>


                <Row>
                <Navbar >
                    <Container>
                    <Nav className="me-auto">
                    <Nav.Link onClick={() => {window.location.href='/list/genre/action'}}>Action</Nav.Link>
                    <Nav.Link onClick={() => {window.location.href='/list/genre/turn-based'}}>Turn-Based</Nav.Link>
                    <Nav.Link onClick={() => {window.location.href='/list/genre/adventure'}}>Adventure</Nav.Link>
                    <Nav.Link onClick={() => {window.location.href='/list/genre/rpg'}}>RPG</Nav.Link>
                    <Nav.Link>
                        <form className="search-form">
                        <input id="search-bar"  type="text" placeholder="Search.." name="search"
                        value={this.state.search} onChange={this.onChangeSearch}/>
                        <input id="search-submit" type="submit"  value="Submit"/>
                        </form>
                    </Nav.Link>
                    </Nav>





                    </Container>


                </Navbar>



                    {this.state.games.map(currentGames =>(
                    <Col xs={3} className="mb-5" key ={`${currentGames.id}`}>
                    <Card className ="h-100 shadow-sm bg-white rounded">
                    <Card.Img  src={currentGames.photoURL} onError={(e) => {
                                e.target.src = Missing // some replacement image
                                }} variant="top"   height="300px" />
                    <Card.Body className ="d-flex flex-column">
                        <div className ="d-flex mb-2 justify-context-between">
                            <Card.Title className ="mb-0 font-weight-bold">{currentGames.title}</Card.Title>
                            <Badge pill className ="mb-1" bg="success">{currentGames.score}</Badge>
                            
                        </div>
                        {this.state.showAdminBoard  && (
                        <Button className="mt-auto font-weight-bold" variant ="danger" onClick={this.deleteGame.bind(this,currentGames.id)}>Delete</Button>)}
                        {this.state.showAdminBoard  && (
                        <Button className="mt-auto font-weight-bold" variant ="warning"
                        onClick= { () => this.gotToUpdateDetails(currentGames.id)}>Update
                        </Button>)}
                        <Button className="mt-auto font-weight-bold"
                                variant ="secondary"
                                onClick= { () => this.goToDetails(currentGames.id)}
                        >See Details
                        </Button>
                    </Card.Body>
                    </Card>
                    </Col>
                    ))}
                </Row>
            </Container>



            // <div className ="gamelist-box">




   /* { <form onSubmit={this.submitForm}>

    <li><Link className ="navbar_links"   onClick={() => {window.location.href='/list/genre/action'}}>Action</Link></li>
    <li><Link className ="navbar_links"   onClick={() => {window.location.href='/list/genre/turn-based'}}>Turn-Based</Link></li>
    <li><Link className ="navbar_links"   onClick={() => {window.location.href='/list/genre/adventure'}}>Adventure</Link></li>
    <li><Link className ="navbar_links"   onClick={() => {window.location.href='/list/genre/rpg'}}>RPG</Link></li>





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
                            <li><Link className ="navbar_links" to= {'/gameDetails/'+game.id}>{game.title}</Link></li>
                            
                            </td>
                      
                        
                            {showAdminBoard  && (
              <div>
                        <button id = "edit-button"> <Link to={"edit/"+game.id}><img src={edit} id ="edit-image"></img></Link></button>
                        <button id = "delete-button" onClick={this.deleteGame.bind(this,game.id)}><img src ={bin} id ="delete-image"/></button>
              </div>
            )}

                        






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

            </div> }*/
                
        //   </div>
        )
    }
}
export default GameList;