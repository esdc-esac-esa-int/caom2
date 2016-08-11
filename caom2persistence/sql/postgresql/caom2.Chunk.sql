
drop table if exists caom2.Chunk;

create table caom2.Chunk
(
    productType varchar(64),
    naxis integer,
    positionAxis1 integer,
    positionAxis2 integer,
    energyAxis integer,
    timeAxis integer,
    polarizationAxis integer,
    observableAxis integer,

    position_coordsys varchar(16),
    position_equinox double precision,
    position_resolution double precision,

    position_axis_axis1_ctype varchar(16),
    position_axis_axis1_cunit varchar(16),
    position_axis_axis2_ctype varchar(16),
    position_axis_axis2_cunit varchar(16),
    position_axis_error1_syser double precision,
    position_axis_error1_rnder double precision,
    position_axis_error2_syser double precision,
    position_axis_error2_rnder double precision,
--    position_axis_range text,
    position_axis_range_start_coord1_pix double precision,
    position_axis_range_start_coord1_val double precision,
    position_axis_range_start_coord2_pix double precision,
    position_axis_range_start_coord2_val double precision,
    position_axis_range_end_coord1_pix double precision,
    position_axis_range_end_coord1_val double precision,
    position_axis_range_end_coord2_pix double precision,
    position_axis_range_end_coord2_val double precision,
    position_axis_bounds text,
--    position_axis_function text,
    position_axis_function_dimension_naxis1 bigint,
    position_axis_function_dimension_naxis2 bigint,
    position_axis_function_refcoord_coord1_pix double precision,
    position_axis_function_refCoord_coord1_val double precision,
    position_axis_function_refCoord_coord2_pix double precision,
    position_axis_function_refCoord_coord2_val double precision,
    position_axis_function_cd11 double precision,
    position_axis_function_cd12 double precision,
    position_axis_function_cd21 double precision,
    position_axis_function_cd22 double precision,

    energy_specsys varchar(16),
    energy_ssysobs varchar(16),
    energy_restfrq double precision,
    energy_restwav double precision,
    energy_velosys double precision,
    energy_zsource double precision,
    energy_velang double precision,
    energy_ssyssrc varchar(16),
    energy_bandpassName varchar(64),
    energy_resolvingPower double precision,
    energy_transition_species varchar(32),
    energy_transition_transition varchar(32),

    energy_axis_axis_ctype varchar(16),
    energy_axis_axis_cunit varchar(16),
    energy_axis_error_syser double precision,
    energy_axis_error_rnder double precision,
--    energy_axis_range text,
    energy_axis_range_start_pix double precision,
    energy_axis_range_start_val double precision,
    energy_axis_range_end_pix double precision,
    energy_axis_range_end_val double precision,
    energy_axis_bounds text,
--    energy_axis_function text,
    energy_axis_function_naxis bigint,
    energy_axis_function_refCoord_pix double precision,
    energy_axis_function_refCoord_val double precision,
    energy_axis_function_delta double precision,

    time_timesys varchar(16),
    time_trefpos varchar(16),
    time_mjdref double precision,
    time_exposure double precision,
    time_resolution double precision,
    time_axis_axis_ctype varchar(16),
    time_axis_axis_cunit varchar(16),
    time_axis_error_syser double precision,
    time_axis_error_rnder double precision,
--    time_axis_range text,
    time_axis_range_start_pix double precision,
    time_axis_range_start_val double precision,
    time_axis_range_end_pix double precision,
    time_axis_range_end_val double precision,
    time_axis_bounds text,
--    time_axis_function text,
    time_axis_function_naxis bigint,
    time_axis_function_refCoord_pix double precision,
    time_axis_function_refCoord_val double precision,
    time_axis_function_delta double precision,

    polarization_axis_axis_ctype varchar(16),
    polarization_axis_axis_cunit varchar(16),
    polarization_axis_error_syser double precision,
    polarization_axis_error_rnder double precision,
--    polarization_axis_range text,
    polarization_axis_range_start_pix double precision,
    polarization_axis_range_start_val double precision,
    polarization_axis_range_end_pix double precision,
    polarization_axis_range_end_val double precision,
    polarization_axis_bounds text,
--    polarization_axis_function text,
    polarization_axis_function_naxis bigint,
    polarization_axis_function_refCoord_pix double precision,
    polarization_axis_function_refCoord_val double precision,
    polarization_axis_function_delta double precision,

    observable_dependent_axis_ctype varchar(64),
    observable_dependent_axis_cunit varchar(64),
    observable_dependent_bin bigint,
    observable_independent_axis_ctype varchar(64),
    observable_independent_axis_cunit varchar(64),
    observable_independent_bin bigint,

-- optimisation
    metaReadAccessGroups tsvector default '',

-- internal
    metaRelease timestamp,
    obsID uuid not null, -- change: UUID
    planeID uuid not null, -- change: UUID
    artifactID uuid not null, -- change: UUID
    partID uuid not null references caom2.Part (partID), -- change: UUID
    chunkID uuid not null primary key using index tablespace caom_index, -- change: UUID
    lastModified timestamp not null,
    maxLastModified timestamp not null,
    stateCode int not null
)
tablespace caom_data
;

-- this is for Part join Chunk
create index i_partID on caom2.Chunk (partID)
tablespace caom_index
;

cluster i_partID on caom2.Chunk
;

-- this is for asset updates
create index ic_planeID on caom2.Chunk (planeID)
tablespace caom_index
;
