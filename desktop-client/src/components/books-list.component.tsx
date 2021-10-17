import {Component, ChangeEvent} from "react";
import BookService from "../services/book.service";
import {Link} from "react-router-dom";
import IBook from '../types/book.type';

type Props = {};

type State = {
    books: Array<IBook>,
    currentBook: IBook | null,
    currentIndex: number,
    searchName: string
};

export default class BooksList extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.onChangeSearchName = this.onChangeSearchName.bind(this);
        this.retrieveBooks = this.retrieveBooks.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveBook = this.setActiveBook.bind(this);
        this.removeAllBooks = this.removeAllBooks.bind(this);
        this.searchName = this.searchName.bind(this);

        this.state = {
            books: [],
            currentBook: null,
            currentIndex: -1,
            searchName: ""
        };
    }

    componentDidMount() {
        this.retrieveBooks();
    }

    onChangeSearchName(e: ChangeEvent<HTMLInputElement>) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    retrieveBooks() {
        console.log("Get all books!")
        window.open('oauth2/authorization/keycloak', '_self');
        BookService.getAll()
            .then(response => {
                this.setState({
                    books: response.data
                });
                console.log(response.data);
            })
            .catch(e => {

                console.log(e);
            });
    }

    refreshList() {
        this.retrieveBooks();
        this.setState({
            currentBook: null,
            currentIndex: -1
        });
    }

    setActiveBook(tutorial: IBook, index: number) {
        this.setState({
            currentBook: tutorial,
            currentIndex: index
        });
    }

    removeAllBooks() {
        BookService.deleteAll()
            .then(response => {
                console.log(response.data);
                this.refreshList();
            })
            .catch(e => {
                console.log(e);
            });
    }

    searchName() {
        this.setState({
            currentBook: null,
            currentIndex: -1
        });

        BookService.findByName(this.state.searchName)
            .then(response => {
                this.setState({
                    books: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { searchName, books, currentBook, currentIndex } = this.state;

        return (
            <div className="list row">
                <div className="col-md-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by name"
                            value={searchName}
                            onChange={this.onChangeSearchName}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={this.searchName}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <h4>Books List</h4>

                    <ul className="list-group">
                        {books &&
                        books.map((book: IBook, index: number) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveBook(book, index)}
                                key={index}
                            >
                                {book.name}
                            </li>
                        ))}
                    </ul>

                    <button
                        className="m-3 btn btn-sm btn-danger"
                        onClick={this.removeAllBooks}
                    >
                        Remove All
                    </button>
                </div>
                <div className="col-md-6">
                    {currentBook ? (
                        <div>
                            <h4>Tutorial</h4>
                            <div>
                                <label>
                                    <strong>Id:</strong>
                                </label>{" "}
                                {currentBook.id}
                            </div>
                            <div>
                                <label>
                                    <strong>Name:</strong>
                                </label>{" "}
                                {currentBook.name}
                            </div>

                            <Link
                                to={"/books/" + currentBook.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br />
                            <p>Please click on a Book...</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}
