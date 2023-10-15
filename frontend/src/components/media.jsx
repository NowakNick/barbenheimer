import React from "react";
import { NavLink } from "react-router-dom";
import { deleteMedia } from "../axios";
import { useNavigate } from "react-router-dom";

export default function Media(props) {
  const navigate = useNavigate();

  const onDelete = async () => {
    if (await deleteMedia(props.data.id)) {
      navigate("/");
    } else {
      //TODO: Make warning
      console.log("Delete Failed!");
    }
  };

  return (
    <div className="media-item col-12 col-md-6 col-lg-4 col-xl-4 col-xxl-3">
      <div className="card">
        <img
          src="/assets/test-image-1.png" //TODO: dynamic image
          className="card-img-top"
          alt={props.data.name}
        />
        <div className="card-body">
          <h5 className="card-title">{props.data.name}</h5>
          <p className="card-text">{props.data.type}</p>
          <div className="d-grid gap-2 d-flex justify-content-between">
            <NavLink
              className="btn btn-primary px-3"
              type="button"
              to={"/edit/" + props.data.id}
            >
              Edit
            </NavLink>
            <button
              onClick={onDelete}
              className="btn btn-danger px-3"
              type="button"
            >
              <i className="bi bi-trash"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
