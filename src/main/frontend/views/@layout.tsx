import {NavLink, Outlet} from "react-router-dom";
import React from "react";

export default function Layout(){
    return (
        <div className="p-m">
            <nav>
                <NavLink to="/" className="btn btn-outline-info m-1">Home</NavLink>
                <NavLink to="/chat" className="btn btn-outline-info m-1">Chat</NavLink>
            </nav>
            <main>
                <Outlet></Outlet>
            </main>
        </div>
    )
}