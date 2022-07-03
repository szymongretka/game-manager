import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreboardViewComponent } from './scoreboard-view.component';

describe('ViewComponent', () => {
  let component: ScoreboardViewComponent;
  let fixture: ComponentFixture<ScoreboardViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScoreboardViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScoreboardViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
