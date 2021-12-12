import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';
class AddToList extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;
        this.gameChange = this.gameChange.bind(this);
        this.submitGame = this.submitGame.bind(this);
    }

    initalState ={
    id:'',userid:'',rating:'',review:'',status:''
};

    componentDidMount(){
        const gameId = +this.props.match.params.id;
        if(gameId){
            this.setState({
                id: gameId
              });
            
        }
        const user = AuthService.getCurrentUser();
        if (user) {
            this.setState({
              userid: user.id
            });
          }
    }





    cancelGame=()=>{
        this.setState(()=> this.initalState);
    }

    gameChange(event){
        this.setState({
            [event.target.name]:event.target.value
        
        });
    }



    submitGame = event =>{
        event.preventDefault(); 
        
    const userList = {
        gameid: this.state.id,
        userid: this.state.userid,
        rating:this.state.rating,
        status:this.state.status,
        review:this.state.review,


    };
    axios.post("http://localhost:8080/userlist",userList)
    setTimeout(()=>this.gameList(),10);

    };




    gameList=()=>{
        this.props.history.push('/list');
    }



    render(){
        const {status,review,rating,id,userid} = this.state;
        return(
            

            <div className ="addgame-form">
                <form onReset={this.cancelGame} onSubmit={this.submitGame} id="gameFormId">

                <label for="status">Status</label>
                <input type="text" id="status" name="status" placeholder="Enter game status.."
                value ={status} onChange={this.gameChange} />
                <label for="rating">Rating</label>
                <input type="number" id="rating" name="rating" placeholder="Enter game rating"
                value ={rating} onChange={this.gameChange}/>
                <label for="review">review</label>
                <input type="text" id="review" name="review" placeholder="Enter game review.."
                value ={review} onChange={this.gameChange}/>

               
                </form>
          </div>
        )
    }
}
export default AddToList;