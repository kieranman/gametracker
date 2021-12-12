import React from 'react';
import axios from 'axios';
import './pages.css';
class Game extends React.Component{
    constructor(props){
        super(props);
        this.state=this.initalState;
        this.gameChange = this.gameChange.bind(this);
        this.submitGame = this.submitGame.bind(this);
    }

    initalState ={
    id:'',title:'',genre1:'',genre2:'',genre3:'',genre4:'',photoURL:'',synopsis:''
};

    componentDidMount(){
        const gameId = +this.props.match.params.id;
        if(gameId){
            this.getGameById(gameId);
            
        }
    }


    getGameById= (gameId)=>{
        axios.get("http://localhost:8080/games/"+gameId)
        .then(response =>{
            if(response.data !=null){
                this.setState({
                    id:response.data.id,
                    title:response.data.title,
                    photoURL:response.data.photoURL,
                    synopsis:response.data.synopsis,
                    genre1:response.data.genre1,
                    genre2:response.data.genre2,
                    genre3:response.data.genre3,
                    genre4:response.data.genre4

                });
            }
        }).catch((error)=>{
            console.error("error - "+error)
        


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



    submitGame = event =>{
        event.preventDefault(); 
        
    const game = {
        title: this.state.title,
        photoURL:this.state.photoURL,
        synopsis:this.state.synopsis,
        genre1:this.state.genre1,
        genre2:this.state.genre2,
        genre3:this.state.genre3,
        genre4:this.state.genre4
    };
    axios.post("http://localhost:8080/games",game)
    setTimeout(()=>this.gameList(),10);

    };








    updateGame = event =>{
        event.preventDefault(); 
        
    const game = {
        id:this.state.id,
        title: this.state.title,
        photoURL:this.state.photoURL,
        synopsis:this.state.synopsis,
        genre1:this.state.genre1,
        genre2:this.state.genre2,
        genre3:this.state.genre3,
        genre4:this.state.genre4
    };
    axios.put("http://localhost:8080/games/"+this.state.id,game)
        .then(response => {
            
            if(response.data !=null){
 
                setTimeout(()=>this.gameList(),0);
            }
        });
    };

    gameList=()=>{
        this.props.history.push('/list');
    }



    render(){
        const {title,photoURL,genre1,genre2,genre3,genre4,synopsis} = this.state;
        return(
            

            <div className ="addgame-form">
                <form onReset={this.cancelGame} onSubmit={this.state.id? this.updateGame:this.submitGame} id="gameFormId">

                <label for="title">Title</label>
                <input type="text" id="title" name="title" placeholder="Enter game title.."
                value ={title} onChange={this.gameChange} />
                <label for="photo">Cover Photo URL</label>
                <input type="text" id="photoURL" name="photoURL" placeholder="Enter game cover photo url.."
                value ={photoURL} onChange={this.gameChange}/>
                <label for="synopsis">Synopsis</label>
                <input type="text" id="synopsis" name="synopsis" placeholder="Enter game description.."
                value ={synopsis} onChange={this.gameChange}/>

                <label for="genre">Genre</label><br/>
                <select id="genre1" name="genre1"
                value ={genre1} onChange={this.gameChange}>
                    <option value=""></option>
                    <option value="action">Action</option>
                    <option value="adventure">Adventure</option>
                    <option value="rpg">RPG</option>
                    <option value="mmo">MMO</option>
                    <option value="turn-based">Turn Based</option>
                    <option value="sandbox">Sandbox</option>
                    <option value="puzzle">Puzzle</option>
                    <option value="fps">FPS</option>
                    <option value="simulation">Simulation</option>
                    <option value="sports">Sports</option>
                    <option value="moba">MOBA</option>
                </select>
                <select id="genre2" name="genre2"
                value ={genre2} onChange={this.gameChange}>
                    <option value=""></option>
                    <option value="action">Action</option>
                    <option value="adventure">Adventure</option>
                    <option value="rpg">RPG</option>
                    <option value="mmo">MMO</option>
                    <option value="turn-based">Turn Based</option>
                    <option value="sandbox">Sandbox</option>
                    <option value="puzzle">Puzzle</option>
                    <option value="fps">FPS</option>
                    <option value="simulation">Simulation</option>
                    <option value="sports">Sports</option>
                    <option value="moba">MOBA</option>
                </select>
                <select id="genre3" name="genre3"
                value ={genre3} onChange={this.gameChange}>
                    <option value=""></option>
                    <option value="action">Action</option>
                    <option value="adventure">Adventure</option>
                    <option value="rpg">RPG</option>
                    <option value="mmo">MMO</option>
                    <option value="turn-based">Turn Based</option>
                    <option value="sandbox">Sandbox</option>
                    <option value="puzzle">Puzzle</option>
                    <option value="fps">FPS</option>
                    <option value="simulation">Simulation</option>
                    <option value="sports">Sports</option>
                    <option value="moba">MOBA</option>
                </select>
                <select id="genre4" name="genre4"
                value ={genre4} onChange={this.gameChange}>
                    <option value=""></option>
                    <option value="action">Action</option>
                    <option value="adventure">Adventure</option>
                    <option value="rpg">RPG</option>
                    <option value="mmo">MMO</option>
                    <option value="turn-based">Turn Based</option>
                    <option value="sandbox">Sandbox</option>
                    <option value="puzzle">Puzzle</option>
                    <option value="fps">FPS</option>
                    <option value="simulation">Simulation</option>
                    <option value="sports">Sports</option>
                    <option value="moba">MOBA</option>
                </select><br/>
                    <input  type="submit" value="Submit"/>
                    <input type="reset" value="Reset"/>


                </form>
          </div>
        )
    }
}
export default Game;