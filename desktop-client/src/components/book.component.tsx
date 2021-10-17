import { Component, ChangeEvent } from "react";
import { RouteComponentProps } from 'react-router-dom';

import BookService from "../services/book.service";
import IBook from "../types/book.type";

interface RouterProps { // type for `match.params`
    id: string; // must be type `string` since value comes from the URL
}

type Props = RouteComponentProps<RouterProps>;

type State = {
    currentBook: IBook;
    message: string;
}

export default class Book extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeId = this.onChangeId.bind(this);
        this.getBook = this.getBook.bind(this);
        this.updateBook = this.updateBook.bind(this);
        this.deleteBook = this.deleteBook.bind(this);

        this.state = {
            currentBook: {
                id: null,
                name: ""
            },
            message: "",
        };
    }

    componentDidMount() {
        this.getBook(this.props.match.params.id);
    }

    onChangeId(e: ChangeEvent<HTMLInputElement>) {
        const id = e.target.value;

        this.setState(function (prevState) {
            return {
                currentBook: {
                    ...prevState.currentBook,
                    id: id,
                },
            };
        });
    }

    onChangeName(e: ChangeEvent<HTMLInputElement>) {
        const name = e.target.value;

        this.setState(function (prevState) {
            return {
                currentBook: {
                    ...prevState.currentBook,
                    name: name,
                },
            };
        });
    }


    getBook(id: string) {
        BookService.get(id)
            .then((response) => {
                this.setState({
                    currentBook: response.data,
                });
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    }

    updateBook() {
        BookService.update(
            this.state.currentBook,
            this.state.currentBook.id
        )
            .then((response) => {
                console.log(response.data);
                this.setState({
                    message: "The book was updated successfully!",
                });
            })
            .catch((e) => {
                console.log(e);
            });
    }

    deleteBook() {
        BookService.delete(this.state.currentBook.id)
            .then((response) => {
                console.log(response.data);
                this.props.history.push("/books");
            })
            .catch((e) => {
                console.log(e);
            });
    }

    render() {
        const { currentBook } = this.state;

        return (
            <div>
                {currentBook ? (
                    <div className="edit-form">
                        <h4>Tutorial</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="title">Id</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="id"
                                    value={currentBook.id}
                                    onChange={this.onChangeId}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="name">Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="name"
                                    value={currentBook.name}
                                    onChange={this.onChangeName}
                                />
                            </div>

                        </form>

                        <button
                            className="badge badge-danger mr-2"
                            onClick={this.deleteBook}
                        >
                            Delete
                        </button>

                        <button
                            type="submit"
                            className="badge badge-success"
                            onClick={this.updateBook}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a Book...</p>
                    </div>
                )}
            </div>
        );
    }
}