-- remove unused indices to improve space used and transaction performance

-- plane.position.bounds.center
drop index if exists <schema>.plane_position_i2;

-- observation.telescope.name + varchar_pattern_ops 
drop index if exists <schema>.i_telescope_pattern;

-- plane.position.bounds.area
drop index if exists <schema>.plane_position_i3;

-- plane.energy.sampleSize
drop index if exists <schema>.plane_energy_iss;

-- plane.energy.bounds.width
drop index if exists <schema>.plane_energy_ibw;

-- observation.proposal.project
drop index if exists <schema>.i_proposal_project;

-- plane.provenance.runid + varchar_pattern_ops
drop index if exists <schema>.i_provenance_runid_pattern;

-- remove unused tables
drop table if exists <schema>.HarvestSkip;
