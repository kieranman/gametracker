import React from 'react';
import './pages.css';
import {
Link
  } from "react-router-dom";
  import EventBus from "../common/EventBus"
  import AuthService from "../services/AuthService"

class Navigation extends React.Component{
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);


    this.state = {
      currentUser: undefined,
      showAdminBoard: false
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }

    EventBus.on("logout", () => {
      this.logOut();
    });

  }
  componentWillUnmount() {
    EventBus.remove("logout");
  }

  logOut() {
    AuthService.logout();
    this.setState({
      currentUser: undefined,
    });
  }

  

    render(){
      const { currentUser, } = this.state;
        return(
<navbar>
        <ul className="main-nav-ul">
            <li className="nav_items"><Link to={'/'}className ="navbar_links">Home</Link></li>
            {this.state.showAdminBoard  && (
            <li className="nav_items"><Link to={'/add'}className ="navbar_links">Add Game</Link></li>)}
            <li className="nav_items"><Link to={'/list'}className ="navbar_links">Game List</Link></li>
            <li className="nav_items"><Link to={'/login'}className ="navbar_links">Login</Link></li>
            <li className="nav_items"><Link to={'/signup'}className ="navbar_links">SignUp</Link></li>

            {currentUser && (
            <li className="nav_items"><Link onClick={this.logOut} to={''} className ="navbar_links">Logout</Link></li>)}

            {currentUser ? (

              <li className="nav_items">
                <Link to={"/userlist"} className="navbar_links">
                  {currentUser.username}
                </Link>
              </li>
          ) : (
            <div>
            </div>
          )}


            

          




    

          </ul>
          
</navbar>







            
 
        )
    }
}
export default Navigation;