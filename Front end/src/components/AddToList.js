import React from 'react';
import axios from 'axios';
import './pages.css';
import AuthService from '../services/AuthService';
import GameService from '../services/GameService';
class AddToList extends React.Component{
    constructor(props){
        super(props);
        this.state={gameId:'',rating:'',review:'',gamestatus:'',token:''};
        this.onChangeReview = this.onChangeReview.bind(this);
        this.onChangeRating = this.onChangeRating.bind(this);
        this.onChangeStatus = this.onChangeStatus.bind(this);
        this.submitForm = this.submitForm.bind(this);

    }



    componentDidMount(){
        const gameId = +this.props.match.params.id;
        if(gameId){
            this.setState({
                gameId: gameId
              });
            
        }
        const user = AuthService.getCurrentUser();
        if (user) {
          this.setState({
            token: user.accessToken,
          });
        }
    }
    


    onChangeReview(e) {
        this.setState({
          review: e.target.value
        });
      }
    
      onChangeRating(e) {
        this.setState({
          rating: e.target.value
        });
      }
    
      onChangeStatus(e) {
        this.setState({
          gamestatus: e.target.value
        });
      }
      


    cancelGame=()=>{
        this.setState(()=> this.initalState);
    }

    gameChange(event){
        this.setState({
            [event.target.name]:event.target.value
        
        });
    }



    submitForm(e){
        e.preventDefault(); 
        

   GameService.addToList(
       this.state.rating,
       this.state.review,
       this.state.gamestatus,
       this.state.gameId,
       this.state.token
   )
   setTimeout(()=>this.gameList(),0);
    };




    gameList=()=>{
        this.props.history.push('/list');
    }

    render(){

        return(
            

            <div className ="addgame-form">
                <form onReset={this.cancelGame} onSubmit={this.submitForm} id="gameFormId">

                <label for="status">Status</label>
                <input type="text" id="gamestatus" name="status" placeholder="Enter game status.."
                value ={this.state.gamestatus} onChange={this.onChangeStatus} />
                <label for="rating">Rating</label>
                <input type="text" id="rating" name="rating" placeholder="Enter game rating"
                value ={this.state.rating} onChange={this.onChangeRating}/>
                <label for="review">review</label>
                <input type="text" id="review" name="review" placeholder="Enter game review.."
                value ={this.state.review} onChange={this.onChangeReview}/>
                <input  type="submit" value="Submit"/>
               
                </form>
          </div>
        )
    }
}
export default AddToList;