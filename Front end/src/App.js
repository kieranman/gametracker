import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import Navigation from './components/Navigation';
import Welcome from './components/Welcome';
import Footer from './components/Footer';
import Game from './components/Game';
import GameList from './components/GameList';
import { BrowserRouter as Router , Route, Switch} from 'react-router-dom';
import Login from './components/Login';
import SignUp from './components/SignUp';
import GameDetails from './components/GameDetails';
import AddToList from './components/AddToList';
import UserList from './components/UserList';
import Recommendations from './components/Recommendations';
import Profile from './components/Profile';
import FriendsList from './components/FriendsList';
import ChatRoom from './components/ChatRoom';



function App() {

























  
  const marginTop ={
    marginTop:"30px"
  
  };
 
  return (
    <div className="background">
    <Router>
      <Navigation/>

    <div className="App" style ={marginTop}>
        
          
       
      <Switch>
        <Route path='/' exact component = {Welcome} />
        <Route path='/add' exact component = {Game}/>
        <Route path='/edit/:id' exact component = {Game}/>
        <Route path='/list/:search' exact component = {GameList}/>
        <Route path='/list/genre/:genre' exact component = {GameList}/>
        <Route path='/list' exact component = {GameList}/>
        <Route path='/login' exact component = {Login}/>
        <Route path='/signup' exact component = {SignUp}/>
        <Route path='/gameDetails/:id' exact component = {GameDetails}/>
        <Route path='/addToList/:id' exact component = {AddToList}/>
        <Route path='/userlist' exact component = {UserList}/>
        <Route path='/userlist/:friendId' exact component = {UserList}/>
        <Route path='/recommendations' exact component = {Recommendations}/>
        <Route path='/profile' exact component = {Profile}/>
        <Route path='/friends' exact component = {FriendsList}/>
        <Route path='/chatroom' exact component = {ChatRoom}/>

  
        

      </Switch>
 
      <Footer/>
      
    </div>
    </Router>
    </div>
  );
}

export default App;
