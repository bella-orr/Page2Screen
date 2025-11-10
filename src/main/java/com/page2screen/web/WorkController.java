@RestController
@RequestMapping("/api/works")
public class WorkController {
    private final WorkService workService;  // Changed from ReviewService

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping
    public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest req) {
        WorkResponse work = workService.createWork(req.getTitle(), req.getMediaType(), req.getReleaseYear());
        return ResponseEntity.created(URI.create("/api/works/" + work.id())).body(work);
    }

    @GetMapping("/{workId}")
    public WorkResponse get(@PathVariable UUID workId) {
        return workService.getWorkDetail(workId);
    }
}
