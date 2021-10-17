import http from "../http-common";
import IBook from "../types/book.type"

class TutorialDataService {
    getAll() {
        return http.get<IBook[]>("/books");
    }

    get(id: string) {
        return http.get<IBook>(`/books/${id}`);
    }

    create(data: IBook) {
        return http.post<IBook>("/books", data);
    }

    update(data: IBook, id: any) {
        return http.put<IBook>(`/books/${id}`, data);
    }

    delete(id: any) {
        return http.delete(`/books/${id}`);
    }

    deleteAll() {
        return http.delete(`/books`);
    }

    findByName(name: string) {
        return http.get<IBook[]>(`/books?name=${name}`);
    }
}

export default new TutorialDataService();