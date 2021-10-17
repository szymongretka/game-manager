import {Component, ChangeEvent} from "react";
import BookService from "../services/book.service";
import IBook from '../types/book.type';

type Props = {};

type State = IBook & {
    submitted: boolean
};

export default class AddBook extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeId = this.onChangeName.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.newBook = this.newBook.bind(this);

        this.state = {
            id: null,
            name: "",
            submitted: false
        };
    }

    onChangeId(e: ChangeEvent<HTMLInputElement>) {
        this.setState({
            id: e.target.value
        });
    }

    onChangeName(e: ChangeEvent<HTMLInputElement>) {
        this.setState({
            name: e.target.value
        });
    }

    saveBook() {
        const data: IBook = {
            id: this.state.id,
            name: this.state.name
        };

        BookService.create(data)
            .then(response => {
                this.setState({
                    id: response.data.id,
                    name: response.data.name,
                    submitted: true
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    newBook() {
        this.setState({
            id: null,
            name: "",
            submitted: false
        });
    }

    render() {
        const {submitted, id, name} = this.state;

        return (
            <div className="submit-form">
                {submitted ? (
                    <div>
                        <h4>You submitted successfully!</h4>
                        <button className="btn btn-success" onClick={this.newBook}>
                            Add
                        </button>
                    </div>
                ) : (
                    <div>
                        <div className="form-group">
                            <label htmlFor="id">Id</label>
                            <input
                                type="text"
                                className="form-control"
                                id="id"
                                required
                                value={id}
                                onChange={this.onChangeId}
                                name="id"
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input
                                type="text"
                                className="form-control"
                                id="name"
                                required
                                value={name}
                                onChange={this.onChangeName}
                                name="name"
                            />
                        </div>

                        <button onClick={this.saveBook} className="btn btn-success">
                            Submit
                        </button>
                    </div>
                )}
            </div>
        );
    }
}