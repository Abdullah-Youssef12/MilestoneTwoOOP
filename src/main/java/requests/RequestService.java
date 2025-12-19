package requests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RequestService {
    // simple singleton (in-memory). Replace later with file/db if you want.
    private static final RequestService INSTANCE = new RequestService();
    private final List<Request> requests = new ArrayList<>();

    private RequestService() {}

    public static RequestService getInstance() {
        return INSTANCE;
    }

    public synchronized void addRequest(Request request) {
        requests.add(request);
    }

    public synchronized List<Request> getAllRequests() {
        return Collections.unmodifiableList(new ArrayList<>(requests));
    }

    public synchronized List<Request> getOpenRequests() {
        List<Request> open = new ArrayList<>();
        for (Request r : requests) if (!r.isResolved()) open.add(r);
        return open;
    }

    public synchronized Optional<Request> findById(String requestId) {
        for (Request r : requests) {
            if (r.getRequestId().equals(requestId)) return Optional.of(r);
        }
        return Optional.empty();
    }
}
