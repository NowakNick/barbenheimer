import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { deleteMedia } from "../axios";
import { useNavigate } from "react-router-dom";

export default function Media(props) {
  const navigate = useNavigate();
  const [alert, setAlert] = useState(false);

  const onDelete = async () => {
    await deleteMedia(props.data.id)
      .then((res) => {
        if ((res.status = 200)) {
          setAlert(false);
          navigate("/");
        } else {
          setAlert(true);
          console.log("Delete Failed!");
        }
      })
      .catch((err) => {
        console.log(err.msg);
        return false;
      });
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
          {alert && (
            <div
              id="formIncompleteAlert"
              className="alert alert-danger mt-3"
              role="alert"
            >
              Es ist ein Fehler beim Löschen aufgetreten.
              <br></br>
              Bitte versuchen sie es erneut
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
